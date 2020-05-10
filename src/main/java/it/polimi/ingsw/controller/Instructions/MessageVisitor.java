package it.polimi.ingsw.controller.Instructions;

/**
 * interface implemented by the messages handler, implements the visitor pattern
 */
public interface MessageVisitor {

    void visit(AskForReloadStateInstr msg);
    void visit(BuildInstr msg);
    void visit(ChooseCardInstr msg);
    void visit(ChooseCardListInstr msg);
    void visit(ChoosePosInstr msg);
    void visit(ChooseWorkerInstr msg);
    void visit(FirstPositioningInstr msg);
    void visit(MoveInstr msg);
    void visit(SetNameInstr msg);
    void visit(SetPowerInstr msg);
    void visit(HandleEndGameInstr msg);
    void visit(LoadGameInstr msg);
}
