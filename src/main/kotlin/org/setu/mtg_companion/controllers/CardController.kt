package org.setu.mtg_companion.controllers

import mu.KotlinLogging
//import org.setu.mtg_companion.models.CardMemStore
import org.setu.mtg_companion.models.CardDBStore
import org.setu.mtg_companion.models.CardModel

class CardController {
    //private val cards = CardMemStore()
    private val cards = CardDBStore()
    private val logger = KotlinLogging.logger {}

    init {
        logger.info("Launching MTG Companion App")
        cards.connectToDB()
        //dummyData()
    }

    fun add(card: CardModel){
        cards.createCard(card)
    }

    fun findAll(): List<CardModel>{
        return cards.findAllCards()
    }

    fun findOne(id: Long): CardModel?{
        return cards.findACard(id)
    }

    fun update(card: CardModel){
        cards.updateCard(card)
    }

    fun delete(id: Long){
        val card: CardModel? = cards.findACard(id)
        if(card != null){
            cards.deleteCard(card)
        } else logger.error("Card not deleted...")
    }

    fun dummyData(){
        cards.createCard(CardModel(name = "Tymaret, Chosen from Death", type = "Enchantment", attack = 2, defence = 0,
            neutralColNum = 0, whiteColNum = 0, blackColNum = 2, redColNum = 0, blueColNum = 0, greenColNum = 0, cardText =
            "Tymaret's toughness is equal to your devotion to black. <i>(Each Black in the mana costs of permanents you control counts toward your devotion to black.)</i>\n" +
            "1 Neutral, 1 Black: Exile up to two target cards from graveyards. You gain 1 life for each creature card exiled this way."))
        cards.createCard(CardModel(name = "Might of Murasa", type = "Instant", attack = 0, defence = 0,
            neutralColNum = 1, whiteColNum = 0, blackColNum = 0, redColNum = 0, blueColNum = 0, greenColNum = 1, cardText =
            "Kicker 2 Neutral 1 Green <i>(You may pay an additional 2 Neutral 1 Green as you cast this spell.)</i>\n" +
            "Target creature gets +3/+3 until end of turn. If this spell was kicked, that creature gets +5/+5 until end of turn instead."))
        cards.createCard(CardModel(name = "Wildgrowth Walker", type = "Creature", attack = 1, defence = 3,
            neutralColNum = 1, whiteColNum = 0, blackColNum = 0, redColNum = 0, blueColNum = 0, greenColNum = 1, cardText =
            "Whenever a creature you control explores, put a +1/+1 counter on Wildgrowth Walker and you gain 3 life."))
        cards.createCard(CardModel(name = "Talons of Wildwood", type = "Enchantment", attack = 0, defence = 0,
            neutralColNum = 1, whiteColNum = 0, blackColNum = 0, redColNum = 0, blueColNum = 0, greenColNum = 1, cardText =
            "Enchant creature\n" +
            "Enchanted creature gets +1/+1 and has trample. <i>(It can deal excess combat damage to the player or planeswalker it's attacking.)</i>\n" +
            "2 Neutral 1 Green: Return Talons of Wildwood from your graveyard to your hand."))
        cards.createCard(CardModel(name = "Reave Soul", type = "Sorcery", attack = 0, defence = 0,
            neutralColNum = 1, whiteColNum = 0, blackColNum = 1, redColNum = 0, blueColNum = 0, greenColNum = 0, cardText =
            "Destroy target creature with power 3 or less."))
        cards.createCard(CardModel(name = "Aggressive Mammoth", type = "Creature", attack = 8, defence = 8,
            neutralColNum = 3, whiteColNum = 0, blackColNum = 0, redColNum = 0, blueColNum = 0, greenColNum = 3, cardText =
            "Trample <i>(This creature can deal excess combat damage to the player or planeswalker it's attacking.)</i>\n" +
            "Other creatures you control have trample."))
    }
}