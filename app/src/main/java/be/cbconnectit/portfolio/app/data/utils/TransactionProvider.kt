package be.cbconnectit.portfolio.app.data.utils

import androidx.room.withTransaction
import be.cbconnectit.portfolio.app.data.local.PortfolioDatabase

class TransactionProvider(
    private val db: PortfolioDatabase
) {
    suspend fun <R> runAsTransaction(block: suspend () -> R): R {
        return db.withTransaction(block)
    }
}