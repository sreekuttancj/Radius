package com.radius.data.repository

import com.radius.data.mapper.FacilityOptionLocalMapper
import com.radius.domain.model.business.ExclusionItem
import com.radius.domain.model.business.FacilityData
import com.radius.domain.model.database.*
import com.radius.domain.model.remote.ExclusionItemRemote
import com.radius.domain.model.remote.FacilityItemRemote
import com.radius.domain.repository.FacilityLocalDataSource
import com.radius.domain.repository.FacilityRemoteDataSource
import com.radius.domain.repository.FacilityRepository
import com.radius.domain.util.ExecutionResult
import com.radius.domain.util.NetworkConnection
import com.radius.domain.util.NetworkConnectionError
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class FacilityRepositoryImp @Inject constructor(
    private val remoteDataSource: FacilityRemoteDataSource,
    private val facilityOptionLocalMapper: FacilityOptionLocalMapper,
    private val networkConnection: NetworkConnection,
    private val localDataSource: FacilityLocalDataSource
) : FacilityRepository {
    override fun getFacilityInfo(): Observable<ExecutionResult<FacilityData>> {
        return Observable.concat(
            Observable.just(ExecutionResult.progress(true)),
            fetchDataFromDataSource(),
            Observable.just(ExecutionResult.progress(false))
        )
    }

    private fun fetchDataFromDataSource(): Observable<ExecutionResult<FacilityData>?> {
        return Observable.defer {
            val facilityEntity = localDataSource.getExclusionList()
            if (facilityEntity != null && !facilityEntity.exclusionList.isNullOrEmpty()) {

                fetchDataFromDb()
            } else {
                if (networkConnection.isInternetConnected) {
                    fetchDataFromRemote()
                } else {
                    Observable.just(ExecutionResult.error(error = NetworkConnectionError()))
                }

            }
        }
    }

    private fun fetchDataFromRemote(): Observable<ExecutionResult<FacilityData>?> {
        return remoteDataSource.getFacilityInfo()
            .doOnNext {
                localDataSource.insertFacilities(getFacilitiesFromRemoteData(it.facilitiesList))
            }.doOnNext {
                localDataSource.insertExclusionList(getExclusionListFromRemote(it.exclusionList))
            }
            .flatMap {
                fetchDataFromDb()
            }
    }

    private fun fetchDataFromDb(): Observable<ExecutionResult<FacilityData>?> {
        return Observable.just(localDataSource.getFacilityInfo())
            .zipWith(Observable.just(localDataSource.getExclusionList()),
                { facilityEntity: List<FacilityEntity>, exclusionEntity: ExclusionEntity ->
                    val data = FacilityEntityData(facilityEntity, exclusionEntity)
                    facilityOptionLocalMapper.map(data)
                }).map {
                it?.let {
                    ExecutionResult.success(it)
                }
            }
    }

    private fun getFacilitiesFromRemoteData(facilityList: List<FacilityItemRemote>?): List<FacilityEntity> {
        val facilities = mutableListOf<FacilityEntity>()
        facilityList?.forEach { facility ->
            val options = mutableListOf<FacilityOptionEntity>()

            facility.options.forEach {
                val option = FacilityOptionEntity(
                    id = it.id,
                    name = it.name,
                    icon = it.icon
                )
                options.add(option)
            }

            val facilityEntity = FacilityEntity(
                facilityId = facility.id,
                name = facility.name,
                options = options
            )

            facilities.add(facilityEntity)
        }
        return facilities
    }

    private fun getExclusionListFromRemote(exclusion: List<List<ExclusionItemRemote>>?): ExclusionEntity {
        val exclusionList = mutableListOf<List<ExclusionItem>>()

        exclusion?.forEach { exclusionListRemote ->
            val exclusionItemList = mutableListOf<ExclusionItem>()
            exclusionListRemote.forEach { exclusionItemRemote ->

                val exclusionItem = ExclusionItem(
                    facilityId = exclusionItemRemote.facilityId,
                    optionId = exclusionItemRemote.optionId
                )
                exclusionItemList.add(exclusionItem)
            }

            exclusionList.add(exclusionItemList)
        }
        return ExclusionEntity(exclusionList = exclusionList)
    }
}