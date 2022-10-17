package org.setu.mtg_companion.models

import mu.KotlinLogging
import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

private val logger = KotlinLogging.logger {}
private var lastId: Long = 0
private var connection: Connection? = null
private var statement: Statement? = null

class CardDBStore : CardStore {

    fun connectToDB(): Boolean {
        return try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtg_companion", "root", "")
            statement = connection!!.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
            true
        } catch (ex: Exception){
            logger.error { ex }
            false
        }
    }

    override fun createCard(card: CardModel) {
        try {
            val query: String =
                "INSERT INTO cards (name, type, attack, defence, neutral, white, black, red, blue, green, text) " +
                        "VALUES ('${card.name}', '${card.type}', ${card.attack}, ${card.defence}, ${card.neutralColNum}, " +
                        "${card.whiteColNum}, ${card.blackColNum}, ${card.redColNum}, ${card.blueColNum}, ${card.greenColNum}, '${card.cardText}')"

            statement!!.execute(query)
        } catch (ex: Exception) {
            logger.error { ex }
        }
    }

    override fun findACard(id: Long): CardModel? {
        return try {
            val foundCardData = statement!!.executeQuery("SELECT * FROM cards WHERE id = ${id} LIMIT 1")
            var foundCards = convertToCardModel(foundCardData).get(0)
            logOne(foundCards)
            foundCards
        } catch (ex: Exception) {
            logger.error { ex }
            null
        }
    }

    override fun findAllCards(): List<CardModel> {
        return try {
            val foundCards = statement!!.executeQuery("SELECT * FROM cards")
            convertToCardModel(foundCards)
        } catch (ex: Exception) {
            logger.error { ex }
            listOf()
        }
    }

    override fun updateCard(card: CardModel) {
        TODO("Not yet implemented")
    }

    override fun deleteCard(card: CardModel) {
        TODO("Not yet implemented")
    }

    private fun logOne(card: CardModel) {
        logger.info("$card")
    }
}

private fun convertToCardModel(rs: ResultSet): List<CardModel>{
    var cardList = ArrayList<CardModel>()
    while(rs.next()) {
        cardList.add(CardModel(
            id = rs.getInt("id").toLong(), name = rs.getString("name"), type = rs.getString("type"),
            attack = rs.getInt("attack").toShort(), defence = rs.getInt("defence").toShort(),
            neutralColNum = rs.getInt("neutral").toShort(), whiteColNum = rs.getInt("white").toShort(),
            blackColNum = rs.getInt("black").toShort(), redColNum = rs.getInt("red").toShort(),
            blueColNum = rs.getInt("blue").toShort(), greenColNum = rs.getInt("green").toShort(),
            cardText = rs.getString("text")
        ))
    }
    return cardList.toList()
}

private fun getNextId(): Long{
    return lastId++
}