package edu.clemson.resolve.jetbrains.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public class GenerateVCsAction extends RESOLVEAction {

    private static final Logger LOG = Logger.getInstance("RESOLVEGenerateVCsAction");

    @Override
    public void update(AnActionEvent e) {
        super.update(e); //checks we're dealing with a resolve file (and that's it)

    }
    //IDEAS:
    // o Have a place to view derivations in a cool way.. (maybe have completion for these fragments of assertive code,
    //          navigational features?, etc
    // o Have a meta-language for crafting assertive code blocks and reduce them in real time showing the steps
    // o Have where users can browse/peruse the rules? hmm.

    //for now though, lets just try to do what the web interface does..

    //classes of interest:
    //TextAttributes
    //MarkupModel <-- probably the most likely candidate for a place to start.
    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        if (project == null) {
            LOG.error("actionPerformed (genVCs): no project for " + e);
            return; // whoa!
        }
        VirtualFile resolveFile = getRESOLVEFileFromEvent(e);
        LOG.info("generate VCs actionPerformed " + (resolveFile == null ? "NONE" : resolveFile));
        if (resolveFile == null) return;
        String title = "RESOLVE VC Generation";
        boolean canBeCancelled = true;
        
        commitDoc(project, resolveFile);

    }


}
