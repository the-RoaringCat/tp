package seedu.goldencompass.operation;

import java.util.ArrayDeque;
import java.util.Deque;

public class OperationHistory {
    private static final int CAPACITY = 10;
    private static final int REAL_CAPACITY = CAPACITY + 1; //since current version also takes up a space in stack.

    private final Deque<OperationSnapshot> undoStack;
    private final Deque<OperationSnapshot> redoStack;

    public OperationHistory() {
        undoStack = new ArrayDeque<>();
        redoStack = new ArrayDeque<>();
    }

    /**
     * Pushes the snapshot into undo stack
     * @param snapshot a snapshot
     */
    public void saveSnapshot(OperationSnapshot snapshot) {
        if (undoStack.size() == REAL_CAPACITY) {
            undoStack.removeLast();
        }
        undoStack.addFirst(snapshot);
    }

    /**
     * Returns the OperationSnapshot of the data before the latest command
     * @return
     */
    public OperationSnapshot getUndo() {

        //pop the current version from undo
        OperationSnapshot current = undoStack.pollFirst();
        OperationSnapshot past = undoStack.peekFirst();
        if (past == null) {
            //since no history, push back
            undoStack.addFirst(current);
            return null;
        }

        redoStack.addFirst(current);

        //peek the second version, which is one version behind the current version
        return past;
    }

    /**
     * Returns the OperationSnapshot of the data of the latest command that has been undone.
     * @return the OperationSnapshot of the data of the latest command that has been undone.
     */
    public OperationSnapshot getRedo() {
        //pop from redo
        OperationSnapshot current = redoStack.pollFirst();
        if (current == null) {
            //there is no redo history
            return null;
        }

        //push into undo
        saveSnapshot(current);

        return current;
    }

    /**
     * Clears redo history
     */
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
