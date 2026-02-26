package fr.smarquis.playground.lint.config

import androidx.annotation.Discouraged
import kotlin.random.Random

public abstract class FeatureFlag {
    public fun parentPrint(): Unit = print(this)

    public companion object {
        public operator fun FeatureFlag.invoke(): Boolean = Random.nextBoolean()
    }
}

@Discouraged("Do not use!")
public data object MyDiscouragedFeatureFlag : FeatureFlag() {
    public fun childPrint(): Unit = print(this)
}
