package org.setu.mtg_companion.views

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.setu.mtg_companion.models.CardDBStore

internal class MyViewTest {

    private val testView: MyView = MyView()
    private val testCardDBStore: CardDBStore = CardDBStore()

    @BeforeEach
    fun setUp() {
        testCardDBStore.connectToDB()
    }

    @Test
    fun testAdd(){
        assertEquals(null, testView.addCardData())
    }

    @AfterEach
    fun tearDown() {
    }
}