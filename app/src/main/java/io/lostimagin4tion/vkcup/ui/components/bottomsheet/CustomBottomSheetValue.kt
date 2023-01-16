package io.lostimagin4tion.vkcup.ui.components.bottomsheet

/**
 * Custom reference of [BottomSheetValue] with new [CustomBottomSheetValue.Half] state
 *
 * @see CustomBottomSheetScaffold
 *
 * @author Egor Danilov
 */
enum class CustomBottomSheetValue {
    /**
     * The bottom sheet is visible, but only showing its peek height.
     */
    Collapsed,

    /**
     * The bottom sheet is visible at **halfCoefficient** size of maximum height
     */
    Half,

    /**
     * The bottom sheet is visible at its maximum height.
     */
    Expanded
}
