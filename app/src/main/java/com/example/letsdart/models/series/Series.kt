package com.example.letsdart.models.series

import com.example.letsdart.models.general.Rules

interface Series {
    var id: Long
    var name: String
    var date: Long
    var rules: Rules
}