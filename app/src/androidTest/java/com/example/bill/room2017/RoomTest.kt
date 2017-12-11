package com.example.bill.room2017

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.example.bill.room2017.dao.*
import com.example.bill.room2017.entity.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RoomTest {
    private lateinit var appDatabase : AppDatabase

    private lateinit var userDao : UserDao
    private lateinit var repoDao : RepoDao
    private lateinit var userWithReposDao : UserWithReposDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

        userDao = appDatabase.userDao()
        repoDao = appDatabase.repoDao()
        userWithReposDao = appDatabase.userWithReposDao()
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    // UserDao
    @Test
    fun testInsertedAndRetrievedUsersMatch() {
        val userSet = User(1, "Name", "url")
        userDao.insert(userSet)

        val userGet = userDao.get(1)
        Assert.assertEquals(userSet, userGet)

        val userUpdate = User(1, "Name", "updateUrl")
        userDao.update(userUpdate)
        val userGetUpdate = userDao.get(1)
        Assert.assertEquals(userUpdate, userGetUpdate)
    }

    // RepoDao
    @Test
    fun testRepoFunMatch() {
        val repos = listOf(
                Repo(1, "Name1", "Url1", 1),
                Repo(2, "Name2", "Url2", 1),
                Repo(3, "Name3", "Url3", 1),
                Repo(4, "Name4", "Url4", 2),
                Repo(5, "Name5", "Url5", 3)
        )

        repoDao.insert(repos)

        val reposGet = repoDao.getAllRepos()
        Assert.assertEquals(repos, reposGet)

        val reposByUserId = listOf(
                Repo(1, "Name1", "Url1", 1),
                Repo(2, "Name2", "Url2", 1),
                Repo(3, "Name3", "Url3", 1)
        )

        val reposByUserIdGet = repoDao.findRepositoriesForUser(1)
        Assert.assertEquals(reposByUserId, reposByUserIdGet)

        val repoUpdate =  Repo(4, "Name4Update", "Url4", 2)
        repoDao.update(repoUpdate)

        // @return List<Repo>
        val reposByUserIdUpdateGet = repoDao.findRepositoriesForUser(2)
        Assert.assertEquals(repoUpdate, reposByUserIdUpdateGet[0])
    }

    // UserWithReposDao
    @Test
    fun testRelationFunMatch() {
       val users = (1..3L).map { User(it, "Name$it", "url$it") }

        val repos = listOf(
                Repo(1, "Name1", "Url1", 1),
                Repo(2, "Name2", "Url2", 1),
                Repo(3, "Name3", "Url3", 1),
                Repo(4, "Name4", "Url4", 2),
                Repo(5, "Name5", "Url5", 3)
        )

        val data = listOf(
                UserWithRepos(User(1, "Name1", "url1"), listOf(
                        Repo(1, "Name1", "Url1", 1),
                        Repo(2, "Name2", "Url2", 1),
                        Repo(3, "Name3", "Url3", 1)
                )),
                UserWithRepos(User(2, "Name2", "url2"),  listOf(
                        Repo(4, "Name4", "Url4", 2)
                )),
                UserWithRepos(User(3, "Name3", "url3"),  listOf(
                        Repo(5, "Name5", "Url5", 3)
                ))
        )

        data.forEach{  a ->
            a.repoList.forEach { b ->
                b.userId = a.user.id
            }
            repoDao.insert(a.repoList)
            userDao.insert(a.user)
        }

        val dataGet = userWithReposDao.getUsersWithRepos()

        dataGet.forEachIndexed { index, userWithRepos ->
            Assert.assertEquals(data[index].user, userWithRepos.user)
            Assert.assertEquals(data[index].repoList, userWithRepos.repoList)
        }
    }
}
