package com.example.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ComplaintDao {
    @Query("SELECT * FROM complaints ORDER BY timestamp DESC")
    fun getAllComplaints(): Flow<List<Complaint>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComplaint(complaint: Complaint)

    @Update
    suspend fun updateComplaint(complaint: Complaint)

    @Query("DELETE FROM complaints WHERE id = :id")
    suspend fun deleteComplaint(id: Int)
}

@Dao
interface NoticeDao {
    @Query("SELECT * FROM notices ORDER BY id DESC")
    fun getAllNotices(): Flow<List<Notice>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotices(notices: List<Notice>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotice(notice: Notice)

    @Update
    suspend fun updateNotice(notice: Notice)
}

@Dao
interface ProjectDao {
    @Query("SELECT * FROM projects ORDER BY progress DESC")
    fun getAllProjects(): Flow<List<Project>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProjects(projects: List<Project>)

    @Update
    suspend fun updateProject(project: Project)
}

@Dao
interface PollDao {
    @Query("SELECT * FROM polls ORDER BY id DESC")
    fun getAllPolls(): Flow<List<Poll>>

    @Update
    suspend fun updatePoll(poll: Poll)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPolls(polls: List<Poll>)
}

@Dao
interface CarbonMetricDao {
    @Query("SELECT * FROM carbon_metrics")
    fun getAllMetrics(): Flow<List<CarbonMetric>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMetrics(metrics: List<CarbonMetric>)
}

@Dao
interface AppConfigDao {
    @Query("SELECT * FROM app_config WHERE `key` = :key LIMIT 1")
    suspend fun getConfig(key: String): AppConfig?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setConfig(config: AppConfig)
}

@Database(
    entities = [Complaint::class, Notice::class, Project::class, Poll::class, CarbonMetric::class, AppConfig::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun complaintDao(): ComplaintDao
    abstract fun noticeDao(): NoticeDao
    abstract fun projectDao(): ProjectDao
    abstract fun pollDao(): PollDao
    abstract fun carbonMetricDao(): CarbonMetricDao
    abstract fun appConfigDao(): AppConfigDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getInstance(context: android.content.Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "civictrack_db"
                )
                .fallbackToDestructiveMigration()
                .build().also { INSTANCE = it }
            }
        }
    }
}
