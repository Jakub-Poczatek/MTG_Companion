package org.setu.mtg_companion.models

data class CardModel(
    var id: Long = 0,
    var name: String = "",
    var type: String = "",
    var attack: Short = -1,
    var defence: Short = -1,
    var neutralColNum: Short = -1,
    var whiteColNum: Short = -1,
    var blackColNum: Short = -1,
    var redColNum: Short = -1,
    var blueColNum: Short = -1,
    var greenColNum: Short = -1,
    var cardText: String = ""
)