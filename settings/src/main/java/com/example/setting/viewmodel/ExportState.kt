package com.example.setting.viewmodel

sealed interface ExportState

object ExportStarted : ExportState

object Success : ExportState

data class Error(val throwable: Throwable) : ExportState

object Unknown : ExportState


