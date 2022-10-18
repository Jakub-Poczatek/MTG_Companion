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
    private var cardList = listOf<CardModel>()
    private var observableCardList = FXCollections.observableList(ArrayList<CardModel>())

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
    private var infoTableView = TableView(observableCardList)
    private var singleInfoTableView = TableView(observableCardList)
    private var criteriaComboBox = ComboBox<String>()
    private var searchTextField = TextField()

    private var currentId: Long = 0

    public fun addCardData(){
        val card = createTempCard()

        // Check if card is valid, otherwise log error
        if(cardIsValid(card)
        ) {
            cardController.add(card)
            listAllCardsData()
            resetFields()
        } else {
            logger.error("Card invalid, not created")
            resetFields()
        }
    }

    public fun listAllCardsData(){
        val cards = cardController.findAll()
        cardList = cards
        infoTableView.items = FXCollections.observableList(cards)
        infoTableView.isVisible = true
        singleInfoTableView.isVisible = false
    }

    public fun listOneCardsData(id: String){
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

    public fun updateCardData(){
        val card = createTempCard()
        card.id = currentId
        if(cardIsValid(card)){
            cardController.update(card)
            listOneCardsData(card.id.toString())
            resetFields()
        }
    }

    public fun deleteCard(id: String){
        if(stringIsLong(id)) {
            cardController.delete(id.toLong())
            listAllCardsData()
            search()
        } else logger.error("Invalid Card ID")
    }

    public fun search(){
        val criteria: String = criteriaComboBox.value
        val query: String = searchTextField.text.lowercase()
        val list = ArrayList<CardModel>()
        when (criteria) {
            "ID" -> cardList.forEach{
                if(query in it.id.toString()){
                    list.add(it)
                }
            }
            "Name" -> cardList.forEach{
                if(query in it.name.lowercase()){
                    list.add(it)
                }
            }
            "Type" -> cardList.forEach{
                if(query in it.type.lowercase()){
                    list.add(it)
                }
            }
        }
        infoTableView.items = FXCollections.observableList(list)
    }

    public fun createTempCard(): CardModel{
        emptyToString()

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
        else
            logger.error("Invalid card text, must be not empty and max 512 characters long")

        return card
    }

    public fun stringIsShort(string: String): Boolean{
        return try {
            string.toShort()
            true
        } catch (e: Exception){
            logger.error("String cannot be converted to Short")
            false
        }
    }

    public fun stringIsLong(string: String): Boolean{
        return try{
            string.toLong()
            true
        } catch (e: Exception){
            logger.error("String cannot be converted to Long")
            false
        }
    }

    public fun resetFields(){
        nameTextField.text = ""
        typeComboBox.value = typeComboBox.items[0]
        attackTextField.text = ""
        defenceTextField.text = ""
        neutralColTextField.text = ""
        whiteColTextField.text = ""
        blackColTextField.text = ""
        redColTextField.text = ""
        blueColTextField.text = ""
        greenColTextField.text = ""
        cardTextArea.text = ""
    }

    public fun cardIsValid(card: CardModel): Boolean{
        return card.name.isNotEmpty() && card.attack > -1 && card.defence > -1 && card.neutralColNum > -1 &&
                card.whiteColNum > -1 && card.blackColNum > -1 && card.redColNum > -1 && card.blueColNum > -1 &&
                card.greenColNum > -1 && card.cardText.isNotEmpty()
    }

    public fun emptyToString(){
        if(attackTextField.text.isEmpty())
            attackTextField.text = "0"
        if(defenceTextField.text.isEmpty())
            defenceTextField.text = "0"
        if(neutralColTextField.text.isEmpty())
            neutralColTextField.text = "0"
        if(whiteColTextField.text.isEmpty())
            whiteColTextField.text = "0"
        if(blackColTextField.text.isEmpty())
            blackColTextField.text = "0"
        if(redColTextField.text.isEmpty())
            redColTextField.text = "0"
        if(blueColTextField.text.isEmpty())
            blueColTextField.text = "0"
        if(greenColTextField.text.isEmpty())
            greenColTextField.text = "0"
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
                }
                defenceTextField = textfield {
                    promptText = "defence"
                    prefWidth = 75.0
                }
            }
            hbox{
                neutralColTextField = textfield {
                    promptText = "Neutral"
                    prefWidth = 50.0
                }
                whiteColTextField = textfield {
                    promptText = "White"
                    prefWidth = 50.0
                }
                blackColTextField = textfield {
                    promptText = "Black"
                    prefWidth = 50.0
                }
            }
            hbox{
                redColTextField = textfield {
                    promptText = "Red"
                    prefWidth = 50.0
                }
                blueColTextField = textfield {
                    promptText = "Blue"
                    prefWidth = 50.0
                }
                greenColTextField = textfield {
                    promptText = "Green"
                    prefWidth = 50.0
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
                    readonlyColumn("ID", CardModel::id) {
                        prefWidth = 25.0
                        enableTextWrap()
                    }
                    readonlyColumn("Name", CardModel::name) {
                        prefWidth = 75.0
                        enableTextWrap()
                    }
                    readonlyColumn("Type", CardModel::type) {
                        prefWidth = 80.0
                        enableTextWrap()
                    }
                    readonlyColumn("Atk", CardModel::attack) {
                        prefWidth = 30.0
                        enableTextWrap()
                    }
                    readonlyColumn("Dfc", CardModel::defence) {
                        prefWidth = 30.0
                        enableTextWrap()
                    }
                    readonlyColumn("Neu", CardModel::neutralColNum) {
                        prefWidth = 30.0
                        enableTextWrap()
                    }
                    readonlyColumn("Wht", CardModel::whiteColNum) {
                        prefWidth = 30.0
                        enableTextWrap()
                    }
                    readonlyColumn("Blk", CardModel::blackColNum) {
                        prefWidth = 30.0
                        enableTextWrap()
                    }
                    readonlyColumn("Red", CardModel::redColNum) {
                        prefWidth = 30.0
                        enableTextWrap()
                    }
                    readonlyColumn("Blu", CardModel::blueColNum) {
                        prefWidth = 30.0
                        enableTextWrap()
                    }
                    readonlyColumn("Grn", CardModel::greenColNum) {
                        prefWidth = 30.0
                        enableTextWrap()
                    }
                    readonlyColumn("Text", CardModel::cardText) {
                        prefWidth = 200.0
                        enableTextWrap()
                    }
                    isVisible = false
                }
            }
            borderpane {
                //paddingLeft = 15
                paddingRight = 15
                paddingTop = 15
                paddingBottom = 15
                hgrow = Priority.ALWAYS

                left = hbox {
                    button("List All") {
                        prefWidth = 75.0
                        hgrow = Priority.ALWAYS
                        action { listAllCardsData() }
                    }
                    button("Find") {
                        prefWidth = 75.0
                        action {
                            if(infoTableView.selectionModel.selectedItem != null)
                                listOneCardsData(infoTableView.selectionModel.selectedItem.id.toString())
                        }
                    }
                    button("Delete") {
                        prefWidth = 75.0
                        action {
                            if(infoTableView.selectionModel.selectedItem != null)
                                deleteCard(infoTableView.selectionModel.selectedItem.id.toString())
                        }
                    }
                }
                right = hbox{
                    searchTextField = textfield{
                        promptText = "search"
                        action { search() }
                    }
                    criteriaComboBox = combobox {
                        items = FXCollections.observableArrayList("ID", "Name", "Type")
                        prefWidth = 75.0
                        value = items[0]
                    }
                }
            }
        }
        listAllCardsData()
    }
}