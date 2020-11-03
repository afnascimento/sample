package com.unilever.julia.data.model.parser

import com.unilever.julia.components.model.IComponentsModel

interface IParserModel {
    fun getText(): String
    fun getModel(): IComponentsModel
}