package org.setu.mtg_companion.models

import org.junit.jupiter.api.BeforeEach
import org.setu.mtg_companion.utils.cardIsValid
import org.setu.mtg_companion.utils.stringIsLong
import org.setu.mtg_companion.utils.stringIsShort

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import mu.KotlinLogging

internal class CardModelTest {
    private var validCard = CardModel()
    private var invalidCard = CardModel()
    private val logger = KotlinLogging.logger {}

    @BeforeEach
    fun setUp() {
        validCard = CardModel(1, "name", "type", 1, 1,
                1, 1, 1, 1, 1, 1, "text")
    }

    @Test
    fun testValidCard(){
        assertEquals(true, cardIsValid(validCard))
        assertEquals(false, cardIsValid(invalidCard))
    }

    @Test
    fun testStringIsShort(){
        assertEquals(true, stringIsShort("1", logger))
        assertEquals(false, stringIsShort("notShort", logger))
    }

    @Test
    fun testStringIsLong(){
        assertEquals(true, stringIsLong("1", logger))
        assertEquals(false, stringIsLong("notLong", logger))
    }
}