package com.example.learningandroidapp.util

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.spec.IsolationMode

// @see https://kotest.io/docs/framework/integrations/mocking.html#option-3---tweak-the-isolationmode
class KotestProjectConfig : AbstractProjectConfig() {
    override val isolationMode = IsolationMode.InstancePerTest
}
