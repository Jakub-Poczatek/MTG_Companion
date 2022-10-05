package org.setu.mtg_companion.view

import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import tornadofx.*

class MyView: View() {
    var nameTextField = TextField()
    var typeComboBox = TextField()
    var attackTextField = TextField()
    var defenceTextField = TextField()
    var colourNeutralTextField = TextField()
    var colourWhiteTextField = TextField()
    var colourBlackTextField = TextField()
    var colourRedTextField = TextField()
    var colourBlueTextField = TextField()
    var colourGreenTextField = TextField()

    override val root = hbox {
        // Left pane
        vbox {
            hbox{
                nameTextField = textfield{
                    promptText = "name"
                    prefWidth = 75.0
                }
                combobox<String> {
                    items = FXCollections.observableArrayList("Artifact", "Creature", "Enchantment", "Land", "PlanesWalker")
                    prefWidth = 75.0
                }
            }
            hbox{
                textfield {
                    promptText = "attack"
                    prefWidth = 75.0
                }
                textfield {
                    promptText = "defence"
                    prefWidth = 75.0
                }
            }
            hbox{
                textfield {
                    promptText = "Neutral"
                    prefWidth = 50.0
                }
                textfield {
                    promptText = "White"
                    prefWidth = 50.0
                }
                textfield {
                    promptText = "Black"
                    prefWidth = 50.0
                }
            }
            hbox{
                textfield {
                    promptText = "Red"
                    prefWidth = 50.0
                }
                textfield {
                    promptText = "Blue"
                    prefWidth = 50.0
                }
                textfield {
                    promptText = "Green"
                    prefWidth = 50.0
                }
            }
            textarea {
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