package org.setu.mtg_companion.views

import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.control.ComboBox
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import tornadofx.*
import mu.KotlinLogging

import org.setu.mtg_companion.controllers.CardController
import org.setu.mtg_companion.models.CardModel

class MyView: View() {

    private val cardController = CardController()
    private val logger = KotlinLogging.logger {}

    private var nameTextField = TextField()
    private var typeComboBox = ComboBox<String>()
    private var attackTextField = TextField()
    private var defenceTextField = TextField()
    private var neutralColTextField = TextField()
    private var whiteColTextField = TextField()
    private var blackColTextField = TextField()
    private var redColTextField = TextField()
    private var blueColTextField = TextField()
    private var greenColTextField = TextField()
    private var cardTextArea = TextArea()

    private fun addCardData(
        name: String, type: String, attack: String, defence: String,
        neutral: String, white: String, black: String,
        red: String, blue: String, green: String, cardText: String
    ){
        val card = CardModel()
        // Check name
        if(name.isNotEmpty() && name.length < 64)
            card.name = name
        else
            logger.error("Invalid name, must be not empty and max 64 characters long")

        // type comes from ComboBox so will always be valid
        card.type = type

        // Check numeric values
        if(stringIsShort(attack) && stringIsShort(defence) && stringIsShort(neutral) && stringIsShort(white) &&
            stringIsShort(black) && stringIsShort(red) && stringIsShort(blue) && stringIsShort(green)){
            if(attack.toShort() in 0..99)
                card.attack = attack.toShort()
            if(defence.toShort() in 0..99)
                card.defence = defence.toShort()
            if(neutral.toShort() in 0..99)
                card.neutralColNum = neutral.toShort()
            if(white.toShort() in 0..99)
                card.whiteColNum = white.toShort()
            if(black.toShort() in 0..99)
                card.blackColNum = black.toShort()
            if(red.toShort() in 0..99)
                card.redColNum = red.toShort()
            if(blue.toShort() in 0..99)
                card.blueColNum = blue.toShort()
            if(green.toShort() in 0..99)
                card.greenColNum = green.toShort()
        }

        // Check card text
        if(cardText.isNotEmpty() && cardText.length < 512)
            card.cardText = cardText

        // Check if card is valid, otherwise log error
        if(card.name.isNotEmpty() && card.attack > -1 && card.defence > -1 && card.neutralColNum > -1 &&
            card.whiteColNum > -1 && card.blackColNum > -1 && card.redColNum > -1 && card.blueColNum > -1 &&
            card.greenColNum > -1 && card.cardText.isNotEmpty())
            cardController.add(card)
        else
            logger.error("Card could not be created, invalid fields")
    }

    private fun stringIsShort(string: String): Boolean{
        return try {
            string.toShort()
            true
        } catch (e: Exception){
            logger
            false
        }
    }

    override val root = hbox {
        // Left pane
        vbox {
            hbox{
                nameTextField = textfield{
                    promptText = "name"
                    prefWidth = 75.0
                }
                typeComboBox = combobox {
                    items = FXCollections.observableArrayList("Artifact", "Creature", "Enchantment", "Land", "PlanesWalker")
                    prefWidth = 75.0
                    value = items[0]
                }
            }
            hbox{
                attackTextField = textfield {
                    promptText = "attack"
                    prefWidth = 75.0
                    text = "0"
                }
                defenceTextField = textfield {
                    promptText = "defence"
                    prefWidth = 75.0
                    text = "0"
                }
            }
            hbox{
                neutralColTextField = textfield {
                    promptText = "Neutral"
                    prefWidth = 50.0
                    text = "0"
                }
                whiteColTextField = textfield {
                    promptText = "White"
                    prefWidth = 50.0
                    text = "0"
                }
                blackColTextField = textfield {
                    promptText = "Black"
                    prefWidth = 50.0
                    text = "0"
                }
            }
            hbox{
                redColTextField = textfield {
                    promptText = "Red"
                    prefWidth = 50.0
                    text = "0"
                }
                blueColTextField = textfield {
                    promptText = "Blue"
                    prefWidth = 50.0
                    text = "0"
                }
                greenColTextField = textfield {
                    promptText = "Green"
                    prefWidth = 50.0
                    text = "0"
                }
            }
            cardTextArea = textarea {
                promptText = "Card Text"
                prefWidth = 150.0
                prefHeight = 150.0
                usePrefWidth = true
            }
            hbox{
                paddingTop = 20
                paddingBottom = 15
                alignment = Pos.CENTER
                button("Submit"){
                    hgrow = Priority.ALWAYS
                    action{
                        addCardData(
                            nameTextField.text, typeComboBox.value, attackTextField.text, defenceTextField.text,
                            neutralColTextField.text, whiteColTextField.text, blackColTextField.text,
                            redColTextField.text, blueColTextField.text, greenColTextField.text, cardTextArea.text
                        )
                    }
                }
                button("Update"){
                    hgrow = Priority.ALWAYS
                }
            }
        }
        vbox {
            prefWidth = 350.0
            scrollpane {
                hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
                fitToParentWidth()
                textarea {
                    useMaxHeight = true
                    isEditable = false
                    fitToParentWidth()
                }
            }
            hbox{
                paddingLeft = 15
                paddingRight = 15
                paddingTop = 15
                paddingBottom = 15
                hgrow = Priority.ALWAYS
                stackpane{
                    hgrow = Priority.ALWAYS
                    button("List All"){
                        prefWidth = 75.0
                        hgrow = Priority.ALWAYS
                        alignment = Pos.CENTER_LEFT
                    }
                }
                stackpane{
                    hgrow = Priority.ALWAYS
                    alignment = Pos.CENTER_RIGHT
                    textfield {
                        promptText = "ID"
                        maxWidth = 50.0
                    }
                }
                vbox{
                    hgrow = Priority.ALWAYS
                    alignment = Pos.CENTER_RIGHT
                    button("Find"){
                        prefWidth = 75.0
                    }
                    button("Delete") {
                        prefWidth = 75.0
                    }
                }
            }
        }
    }
}