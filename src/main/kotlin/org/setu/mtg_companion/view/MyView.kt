package org.setu.mtg_companion.view

import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.control.TextField
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
                    isFocusTraversable = false
                    promptText = "name"
                    prefWidth = 75.0
                }
                combobox<String> {
                    isFocusTraversable = false
                    items = FXCollections.observableArrayList("Artifact", "Creature", "Enchantment", "Land", "PlanesWalker")
                    prefWidth = 75.0
                }
            }
            hbox{
                textfield {
                    isFocusTraversable = false
                    promptText = "attack"
                    prefWidth = 75.0
                }
                textfield {
                    isFocusTraversable = false
                    promptText = "defence"
                    prefWidth = 75.0
                }
            }
            hbox{
                textfield {
                    isFocusTraversable = false
                    promptText = "Neutral"
                    prefWidth = 50.0
                }
                textfield {
                    isFocusTraversable = false
                    promptText = "White"
                    prefWidth = 50.0
                }
                textfield {
                    isFocusTraversable = false
                    promptText = "Black"
                    prefWidth = 50.0
                }
            }
            hbox{
                textfield {
                    isFocusTraversable = false
                    promptText = "Red"
                    prefWidth = 50.0
                }
                textfield {
                    isFocusTraversable = false
                    promptText = "Blue"
                    prefWidth = 50.0
                }
                textfield {
                    isFocusTraversable = false
                    promptText = "Green"
                    prefWidth = 50.0
                }
            }
            textarea {
                isFocusTraversable = false
                promptText = "Card Text"
                prefWidth = 150.0
                prefHeight = 150.0
                usePrefWidth = true
            }
            stackpane {
                button("Submit")
            }
        }
        textarea{
            prefWidth = 300.0
            prefHeight = 150.0
            isEditable = false
        }
    }
}