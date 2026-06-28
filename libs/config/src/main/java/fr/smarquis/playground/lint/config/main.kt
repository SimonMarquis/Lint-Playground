package fr.smarquis.playground.lint.config

import fr.smarquis.playground.lint.config.FeatureFlag.Companion.invoke

public fun main() {
    MyDiscouragedFeatureFlag
    MyDiscouragedFeatureFlag.toString()
    MyDiscouragedFeatureFlag.parentPrint()
    MyDiscouragedFeatureFlag.childPrint()
    if (MyDiscouragedFeatureFlag.invoke()) Unit
    if (MyDiscouragedFeatureFlag()) Unit
    print(MyDiscouragedFeatureFlag)
}
