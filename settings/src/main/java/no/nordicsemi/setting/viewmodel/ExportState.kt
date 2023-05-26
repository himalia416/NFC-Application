package no.nordicsemi.setting.viewmodel

sealed interface ExportState

object ExportStarted : ExportState

object ExportSuccess : ExportState

data class ErrorInExport(val throwable: Throwable) : ExportState

object ExportStateUnknown : ExportState


