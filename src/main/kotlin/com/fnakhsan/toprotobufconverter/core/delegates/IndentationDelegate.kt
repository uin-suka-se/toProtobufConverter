package com.fnakhsan.toprotobufconverter.core.delegates

internal class IndentationDelegate {
    fun updateFileBody(body: String) =
        body.replace(TAB, "$SPACE$SPACE")

    companion object {
        private const val SPACE = " "
        private const val TAB = "\t"
    }
}
