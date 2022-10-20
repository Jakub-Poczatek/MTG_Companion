package org.setu.mtg_companion.utils

import mu.KLogger
import org.setu.mtg_companion.models.CardModel

fun cardIsValid(card: CardModel): Boolean{
    // Checks if card is valid, if card contains default values of model then card is invalid
    return card.name.isNotEmpty() && card.attack > -1 && card.defence > -1 && card.neutralColNum > -1 &&
            card.whiteColNum > -1 && card.blackColNum > -1 && card.redColNum > -1 && card.blueColNum > -1 &&
            card.greenColNum > -1 && card.cardText.isNotEmpty()
}

fun stringIsShort(string: String, logger: KLogger): Boolean{
    return try {
        string.toShort()
        true
    } catch (e: Exception){
        logger.error("String cannot be converted to Short")
        false
    }
}

fun stringIsLong(string: String, logger: KLogger): Boolean{
    return try{
        string.toLong()
        true
    } catch (e: Exception){
        logger.error("String cannot be converted to Long")
        false
    }
}