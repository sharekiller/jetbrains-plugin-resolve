package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceOwner;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.PsiFileReference;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.containers.ContainerUtil;
import edu.clemson.resolve.plugin.ConstEleTypes;
import edu.clemson.resolve.plugin.psi.*;
import edu.clemson.resolve.plugin.psi.impl.uses.ResUsesReferenceSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ResPsiImplUtil {

    @NotNull public static String getText(@Nullable ResType o) {
        if (o == null) return "";
        boolean s = o instanceof ResRecordType;
        if (s) {
            PsiElement parent = o.getParent();
            if (parent instanceof ResAbstractTypeDecl) {
                String n = ((ResAbstractTypeDecl)parent).getName();
                String p = ((ResAbstractTypeDecl)parent).getContainingFile().getName();
                if (n != null && p != null) return p + "::" + n;
            }
            return s ? "record {...}" : "";
        }
        String text = o.getText();
        if (text == null) return "";
        return text.replaceAll("\\s+", " ");
    }

    @NotNull public static PsiReference[] getReferences(
            @NotNull ResUsesItem o) {
        return new ResUsesReferenceSet(o).getAllReferences();
    }

    @Nullable public static PsiReference getReference(
            @NotNull ResUsesItem o) {
        PsiReference[] refs = getReferences(o);
        if (refs.length == 0) return null;
        else return refs[0];
    }

    @NotNull public static TextRange getUsesTextRange(
            @NotNull ResUsesItem usesItem) {
        String text = usesItem.getText();
        return !text.isEmpty() ? TextRange.create(0, text.length() - 1) :
                TextRange.EMPTY_RANGE;
    }

    @Nullable public static ResType getResType(@NotNull final ResExpression o,
                                               @Nullable ResolveState context) {
        return null; //TODO TODO TODO
    }

    @Nullable public static ResReference getReference(ResReferenceExpression o) {
        if (o == null || o.getIdentifier() == null) return null;
        return new ResReference(o);
    }

    @Nullable public static PsiReference getReference(
            @NotNull ResTypeReferenceExpression o) {
        return new ResTypeReference(o);
    }

    @Nullable public static ResTypeReferenceExpression getQualifier(
            @NotNull ResTypeReferenceExpression o) {
        return PsiTreeUtil.getChildOfType(o, ResTypeReferenceExpression.class);
    }

    /** ok, in the go plugin don't be fooled by the seeming lack of connection between
     *  UsesReferenceHelper and the FileContextProvider -- these are responsible
     *  for setting getDefaultContext to "resolve/src/" etc...
     */
    @Nullable public static PsiFile resolve(@NotNull ResUsesItem usesItem) {
       PsiReference[] references = usesItem.getReferences();
        for (PsiReference reference : references) {
            if (reference instanceof FileReferenceOwner) {
                PsiFileReference lastFileReference = ((FileReferenceOwner)reference).getLastFileReference();
                PsiElement result = lastFileReference != null ? lastFileReference.resolve() : null;
                return result instanceof PsiFile ? (PsiFile)result : null;
            }
        }
        return null;
    }

    @Nullable public static ResType getResTypeInner(
            @NotNull ResVarDefinition o, @Nullable ResolveState context) {
        PsiElement parent = o.getParent();

        if (parent instanceof ResVarDeclGroup) {
            return ((ResVarDeclGroup)parent).getType();
        }
        return null;
    }

    @NotNull public static ResUsesItem addUsesItem(
            @NotNull ResUsesListImpl usesList, @NotNull String name) {
        Project project = usesList.getProject();
        ResUsesItem newDeclaration =
                ResElementFactory.createUsesItem(project, name);
        List<ResUsesItem> existingUses = usesList.getUsesItems();
        
        ResUsesItem lastUses = ContainerUtil.getLastItem(existingUses);
        GoImportDeclaration importDeclaration = (GoImportDeclaration)importList.addAfter(newImportDeclaration, lastUses);
        PsiElement importListNextSibling = importList.getNextSibling();
        if (!(importListNextSibling instanceof PsiWhiteSpace)) {
            importList.addAfter(GoElementFactory.createNewLine(importList.getProject()), importDeclaration);
            if (importListNextSibling != null) {
                // double new line if there is something valuable after import list
                importList.addAfter(GoElementFactory.createNewLine(importList.getProject()), importDeclaration);
            }
        }
        importList.addBefore(GoElementFactory.createNewLine(importList.getProject()), importDeclaration);
        GoImportSpec result = ContainerUtil.getFirstItem(importDeclaration.getImportSpecList());
        assert result != null;
        return result;
    }

    public static boolean isPrevColonColon(@Nullable PsiElement parent) {
        PsiElement prev = parent == null ? null :
                PsiTreeUtil.prevVisibleLeaf(parent);
        return prev instanceof LeafElement &&
                ((LeafElement)prev).getElementType() == ConstEleTypes.COLONCOLON;
    }
}
