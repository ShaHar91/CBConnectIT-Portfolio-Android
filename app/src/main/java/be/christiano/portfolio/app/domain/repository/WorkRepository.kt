package be.christiano.portfolio.app.domain.repository

import be.christiano.portfolio.app.domain.model.Work
import be.christiano.portfolio.app.data.remote.Result

interface WorkRepository {
    suspend fun fetchAllWorks() : Result<List<Work>>
}