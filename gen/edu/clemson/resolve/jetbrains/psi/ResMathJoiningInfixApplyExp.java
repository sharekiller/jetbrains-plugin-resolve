// This is a generated file. Not intended for manual editing.
package edu.clemson.resolve.jetbrains.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResMathJoiningInfixApplyExp extends ResMathExp {

  @NotNull
  List<ResMathExp> getMathExpList();

  @Nullable
  PsiElement getCat();

  @Nullable
  PsiElement getColoncolon();

  @Nullable
  PsiElement getIntersect();

  @Nullable
  PsiElement getUnion();

}
