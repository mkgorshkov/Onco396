package com.mgorshkov.hig.model.enums;

/**
 * @author Maxim Gorshkov <maxim.gorshkov<at>savoirfairelinux.com>
 */
public enum Stage {
    CT_SCAN, INITIAL_CONTOUR, MD_CONTOUR, CT_PLANNING_SHEET, DOSE_CALCULATION, MD_APPROVE, PHYSICS_QA, READY_FOR_TREATMENT;

    public Stage getNext() {
        return this.ordinal() < Stage.values().length - 1
                ? Stage.values()[this.ordinal() + 1]
                : null;
    }
}
