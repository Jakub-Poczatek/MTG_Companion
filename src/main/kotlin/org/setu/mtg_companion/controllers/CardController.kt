package org.setu.mtg_companion.controllers

import mu.KotlinLogging
import org.setu.mtg_companion.models.CardMemStore
import org.setu.mtg_companion.models.CardModel
import javafx.scene.control.TextArea

class CardController {
    private val cards = CardMemStore()
    private val logger = KotlinLogging.logger {}

    init {
        logger.info("Launching MTG Companion App")
    }

    fun add(card: CardModel){
        cards.createCard(card)
    }

    fun listAll(cardText: TextArea){
        cards.findAllCards()
        cardText.clear()
        for (card in cards.findAllCards()){
            cardText.text += "ID: \t\t\t" + card.id + "\tName: " + card.name +
                    "\nAttack: \t\t" + card.attack + "\tDefence: \t\t" + card.defence +
                    "\nNeutral Cost: \t" + card.neutralColNum + "\tWhite Cost: \t" + card.whiteColNum + "\tBlack Cost: \t" + card.blackColNum +
                    "\nRed Cost: \t" + card.redColNum + "\tBlue Cost: \t" + card.blueColNum + "\tGreen Cost: \t" + card.greenColNum +
                    "\nCard Text: " + card.cardText +"\n\n"
        }
    }
}