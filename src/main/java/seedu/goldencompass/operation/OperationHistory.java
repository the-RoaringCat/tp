package seedu.goldencompass.operation;

import java.util.ArrayDeque;
import java.util.Deque;

public class OperationHistory {
    private static final int CAPACITY = 10;


    private final Deque<OperationSnapshot> undoStack;
    private final Deque<OperationSnapshot> redoStack;

    public OperationHistory() {
        undoStack = new ArrayDeque<>();
        redoStack = new ArrayDeque<>();
    }

    public void saveSnapshot(OperationSnapshot snapshot) {
        if(undoStack.size() == CAPACITY) {
            undoStack.removeLast();
        }
        undoStack.addFirst(snapshot);
    }

    public OperationSnapshot getUndo() {

        //pop the current version from undo
        OperationSnapshot current = undoStack.pollFirst();
        OperationSnapshot past = undoStack.peekFirst();
        if(past == null) {
            //since no history, push back
            undoStack.addFirst(current);
            return null;
        }

        redoStack.addFirst(current);

        //peek the second version, which is one version behind the current version
        return past;
    }

    public OperationSnapshot getRedo() {
        //pop from redo
        OperationSnapshot current = redoStack.pollFirst();
        if(current == null) {
            //there is no redo history
            return null;
        }

        //push into undo
        saveSnapshot(current);

        return current;
    }

    public void clearRedo() {
        redoStack.clear();
    }

    /**
     * This is a workaround to get the command that has been undone.
     * <P>
     * This is because the undo mechanism would replace the data with snapshot of one version earlier than the
     * current version. But each snapshot records the command that results in that version. Since the current version
     * would be pushed into redo stack, redo stack must have at least one element when trying to undo.
     * So we peek the redo stack for the command that has been undone.
     * @return the command that has been undone.
     */
    public String getUndoneCommand() {
        assert redoStack.peekFirst() != null : "There is no redo history, call this after an undo";
        return redoStack.peekFirst().command;
    }
}
