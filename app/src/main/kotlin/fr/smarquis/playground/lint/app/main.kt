package fr.smarquis.playground.lint.app

import fr.smarquis.playground.lint.config.FeatureFlag.Companion.invoke
import fr.smarquis.playground.lint.config.MyDiscouragedFeatureFlag


public fun main() {
    MyDiscouragedFeatureFlag
    MyDiscouragedFeatureFlag.toString()
    MyDiscouragedFeatureFlag.parentPrint()
    MyDiscouragedFeatureFlag.childPrint()
    if (MyDiscouragedFeatureFlag.invoke()) Unit
    if (MyDiscouragedFeatureFlag()) Unit
    print(MyDiscouragedFeatureFlag)
}

