// This is a generated file. Not intended for manual editing.
package edu.clemson.resolve.jetbrains.psi.impl;

import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;

import static edu.clemson.resolve.jetbrains.ResTypes.*;
import edu.clemson.resolve.jetbrains.psi.*;

public class ResReferenceExpImpl extends ResExpImpl implements ResReferenceExp {

  public ResReferenceExpImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ResVisitor visitor) {
    visitor.visitReferenceExp(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ResVisitor) accept((ResVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public PsiElement getIdentifier() {
    return findNotNullChildByType(IDENTIFIER);
  }

  @NotNull
  public ResReference getReference() {
    return ResPsiImplUtil.getReference(this);
  }

  @Nullable
  public ResReferenceExp getQualifier() {
    return ResPsiImplUtil.getQualifier(this);
  }

  public boolean expectedToReferenceImportedModuleOrModuleAlias() {
    return ResPsiImplUtil.shouldReferenceModule(this);
  }

}
