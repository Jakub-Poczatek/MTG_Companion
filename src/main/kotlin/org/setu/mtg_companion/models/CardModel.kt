package org.setu.mtg_companion.models

class CardModel(
    var id: Long = 0,
    var name: String = "",
    var type: String = "",
    var attack: Short = 0,
    var defence: Short = 0,
    var neutralColNum: Int = 0,
    var whiteColNum : Int = 0,
    var blackColNum : Int = 0,
    var redColNum : Int = 0,
    var blueColNum : Int = 0,
    var greenColNum : Int = 0,
    var cardText: String = ""
) {
}