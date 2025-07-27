// ItemViewModelTest.kt
package com.company.fetchexercise

import com.company.fetchexercise.models.Item
import com.company.fetchexercise.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ItemViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    // Fake ApiService implementation for test
    private val fakeApiService = object : ApiService {
        override suspend fun fetchItems(): List<Item> = listOf(
            Item(listId = 2, id = 1, name = "Fetch"),
            Item(listId = 1, id = 2, name = "Dog"),
            Item(listId = 1, id = 3, name = ""),     // filtered out
            Item(listId = 2, id = 4, name = null),   // filtered out
            Item(listId = 1, id = 5, name = "Android"),
        )
    }

    private lateinit var viewModel: ItemViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ItemViewModel(apiService = fakeApiService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testGroupedItems_filtersSortsGroups() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()

        val grouped = viewModel.groupedItems.value

        assertEquals(2, grouped.size)

        val group1Names = grouped[1]?.map { it.name }
        assertEquals(listOf("Android", "Dog"), group1Names)

        val group2Names = grouped[2]?.map { it.name }
        assertEquals(listOf("Fetch"), group2Names)
    }
}
