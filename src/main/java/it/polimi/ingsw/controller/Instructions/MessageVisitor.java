package it.polimi.ingsw.controller.Instructions;

/**
 * interface implemented by the messages handler, implements the visitor pattern
 */
public interface MessageVisitor {

    void visit(AskForReloadStateNotification msg);
    void visit(BuildNotification msg);
    void visit(ChooseCardNotification msg);
    void visit(ChooseCardListNotification msg);
    void visit(ChoosePosNotification msg);
    void visit(ChooseWorkerNotification msg);
    void visit(FirstPositioningNotification msg);
    void visit(MoveNotification msg);
    void visit(SetNameNotification msg);
    void visit(SetPowerNotification msg);
    void visit(DisconnectionNotification msg);
    void visit(LoadGameNotification msg);
    void visit(EliminationNotification msg);
    void visit(WinNotification msg);
}
