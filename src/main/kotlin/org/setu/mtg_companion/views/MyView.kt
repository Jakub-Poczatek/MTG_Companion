package org.setu.mtg_companion.views

import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.control.ComboBox
import javafx.scene.control.TableView
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
    private var cardsList = FXCollections.observableList(ArrayList<CardModel>())

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
    private var idTextField = TextField()
    private var infoTableView = TableView(cardsList)
    private var singleInfoTableView = TableView(cardsList)

    private var currentId: Long = 0

    private fun addCardData(){
        val card = createTempCard()

        // Check if card is valid, otherwise log error
        if(cardIsValid(card)
        ) {
            cardController.add(card)
            listAllCardsData()
            resetFields()
        } else logger.error("Card invalid, not created")
    }

    private fun listAllCardsData(){
        val cards = cardController.findAll()
        infoTableView.items = FXCollections.observableList(cards)
        infoTableView.isVisible = true
        singleInfoTableView.isVisible = false
    }

    private fun listOneCardsData(id: String){
        if(stringIsLong(id)) {
            val card = cardController.findOne(id.toLong())
            if (card != null) {
                infoTableView.isVisible = false
                singleInfoTableView.isVisible = true
                singleInfoTableView.items = FXCollections.observableList(listOf(card))

                nameTextField.text = card.name
                typeComboBox.value = card.type
                attackTextField.text = card.attack.toString()
                defenceTextField.text = card.defence.toString()
                neutralColTextField.text = card.neutralColNum.toString()
                whiteColTextField.text = card.whiteColNum.toString()
                blackColTextField.text = card.blackColNum.toString()
                redColTextField.text = card.redColNum.toString()
                blueColTextField.text = card.blueColNum.toString()
                greenColTextField.text = card.greenColNum.toString()
                cardTextArea.text = card.cardText

                idTextField.text = ""

                currentId = id.toLong()
            } else logger.error("Could not find card")
        } else logger.error("String cannot be converted to Long")
    }

    private fun updateCardData(){
        val card = createTempCard()
        card.id = currentId
        if(cardIsValid(card)){
            cardController.update(card)
            listOneCardsData(card.id.toString())
            resetFields()
        }
    }

    private fun deleteCard(id: String){
        if(stringIsLong(id)) {
            cardController.delete(id.toLong())
            listAllCardsData()
        } else logger.error("Invalid Card ID")
    }

    private fun createTempCard(): CardModel{
        //assign all variables
        val name = nameTextField.text
        val type = typeComboBox.value
        val attack = attackTextField.text
        val defence = defenceTextField.text
        val neutral = neutralColTextField.text
        val white = whiteColTextField.text
        val black = blackColTextField.text
        val red = redColTextField.text
        val blue = blueColTextField.text
        val green = greenColTextField.text
        val cardText = cardTextArea.text

        val card = CardModel()

        // Check name
        if(name.isNotEmpty() && name.length < 64)
            card.name = name
        else
            logger.error("Invalid name, must be not empty and max 64 characters long")

        // Type comes from ComboBox so will always be valid
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

        return card
    }

    private fun stringIsShort(string: String): Boolean{
        return try {
            string.toShort()
            true
        } catch (e: Exception){
            logger.error("String cannot be converted to Short")
            false
        }
    }

    private fun stringIsLong(string: String): Boolean{
        return try{
            string.toLong()
            true
        } catch (e: Exception){
            logger.error("String cannot be converted to Long")
            false
        }
    }

    private fun resetFields(){
        nameTextField.text = ""
        typeComboBox.value = typeComboBox.items[0]
        attackTextField.text = "0"
        defenceTextField.text = "0"
        neutralColTextField.text = "0"
        whiteColTextField.text = "0"
        blackColTextField.text = "0"
        redColTextField.text = "0"
        blueColTextField.text = "0"
        greenColTextField.text = "0"
        cardTextArea.text = ""
    }

    private fun cardIsValid(card: CardModel): Boolean{
        return card.name.isNotEmpty() && card.attack > -1 && card.defence > -1 && card.neutralColNum > -1 &&
                card.whiteColNum > -1 && card.blackColNum > -1 && card.redColNum > -1 && card.blueColNum > -1 &&
                card.greenColNum > -1 && card.cardText.isNotEmpty()
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
                isWrapText = true

            }
            hbox{
                paddingTop = 20
                paddingBottom = 15
                alignment = Pos.CENTER
                button("Submit"){
                    hgrow = Priority.ALWAYS
                    action{ addCardData() }
                }
                button("Update"){
                    hgrow = Priority.ALWAYS
                    action { updateCardData()}
                }
            }
        }
        vbox {
            stackpane {
                infoTableView = tableview {
                    readonlyColumn("ID", CardModel::id) {
                        prefWidth = 25.0
                    }
                    readonlyColumn("Name", CardModel::name) {
                        prefWidth = 75.0
                    }
                    readonlyColumn("Type", CardModel::type) {
                        prefWidth = 80.0
                    }
                    readonlyColumn("Atk", CardModel::attack) {
                        prefWidth = 30.0
                    }
                    readonlyColumn("Dfc", CardModel::defence) {
                        prefWidth = 30.0
                    }
                    readonlyColumn("Neu", CardModel::neutralColNum) {
                        prefWidth = 30.0
                    }
                    readonlyColumn("Wht", CardModel::whiteColNum) {
                        prefWidth = 30.0
                    }
                    readonlyColumn("Blk", CardModel::blackColNum) {
                        prefWidth = 30.0
                    }
                    readonlyColumn("Red", CardModel::redColNum) {
                        prefWidth = 30.0
                    }
                    readonlyColumn("Blu", CardModel::blueColNum) {
                        prefWidth = 30.0
                    }
                    readonlyColumn("Grn", CardModel::greenColNum) {
                        prefWidth = 30.0
                    }
                    readonlyColumn("Text", CardModel::cardText) {
                        prefWidth = 200.0
                    }
                }
                singleInfoTableView = tableview {
                    column("ID", CardModel::id) {
                        prefWidth = 25.0
                        enableTextWrap()
                    }
                    column("Name", CardModel::name) {
                        prefWidth = 75.0
                        enableTextWrap()
                    }
                    column("Type", CardModel::type) {
                        prefWidth = 80.0
                        enableTextWrap()
                    }
                    column("Atk", CardModel::attack) {
                        prefWidth = 30.0
                        enableTextWrap()
                    }
                    column("Dfc", CardModel::defence) {
                        prefWidth = 30.0
                        enableTextWrap()
                    }
                    column("Neu", CardModel::neutralColNum) {
                        prefWidth = 30.0
                        enableTextWrap()
                    }
                    column("Wht", CardModel::whiteColNum) {
                        prefWidth = 30.0
                        enableTextWrap()
                    }
                    column("Blk", CardModel::blackColNum) {
                        prefWidth = 30.0
                        enableTextWrap()
                    }
                    column("Red", CardModel::redColNum) {
                        prefWidth = 30.0
                        enableTextWrap()
                    }
                    column("Blu", CardModel::blueColNum) {
                        prefWidth = 30.0
                        enableTextWrap()
                    }
                    column("Grn", CardModel::greenColNum) {
                        prefWidth = 30.0
                        enableTextWrap()
                    }
                    column("Text", CardModel::cardText) {
                        prefWidth = 200.0
                        enableTextWrap()
                        isEditable = true
                    }
                    isVisible = false
                }
            }
            hbox{
                paddingLeft = 15
                paddingRight = 15
                paddingTop = 15
                paddingBottom = 15
                hgrow = Priority.ALWAYS

                borderpane{
                    hgrow = Priority.ALWAYS
                    left = button("List All"){
                        prefWidth = 75.0
                        hgrow = Priority.ALWAYS
                        alignment = Pos.CENTER_LEFT
                        action { listAllCardsData() }
                    }
                    center = button("Find"){
                        prefWidth = 75.0
                        alignment = Pos.CENTER_LEFT
                        action { listOneCardsData(infoTableView.selectionModel.selectedItem.id.toString()) }
                    }
                    right = button("Delete") {
                        prefWidth = 75.0
                        alignment = Pos.CENTER_LEFT
                        action {deleteCard(infoTableView.selectionModel.selectedItem.id.toString())}
                    }
                }
            }
        }
    }
}