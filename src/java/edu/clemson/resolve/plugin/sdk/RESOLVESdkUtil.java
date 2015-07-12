package edu.clemson.resolve.plugin.sdk;

import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.ObjectUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

public class RESOLVESdkUtil {

    @Nullable public static VirtualFile suggestSdkDirectory() {
        if (SystemInfo.isWindows) {
            return ObjectUtils.chooseNotNull(LocalFileSystem.getInstance().findFileByPath("C:\\resolve"),
                    LocalFileSystem.getInstance().findFileByPath("C:\\cygwin"));
        }
        if (SystemInfo.isMac || SystemInfo.isLinux) {
         //   String fromEnv = suggestSdkDirectoryPathFromEnv();
         //   if (fromEnv != null) {
         //       return LocalFileSystem.getInstance().findFileByPath(fromEnv);
         //   }
            return LocalFileSystem.getInstance().findFileByPath("/usr/local/lib/");
        }
        return null;
    }

    /**
     * Gets a file reference to compiler executable
     *
     * @param SDKPath path to SDK
     * @return File reference to compiler executable
     */
    public static File getCompilerExecutable(@NotNull String SDKPath) {
        File SDKfolder = new File(SDKPath);

        File[] directoryListing = SDKfolder.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (child.getName().startsWith("resolve-")) {
                    return child;
                }
                // Do something with child
            }
        }
        throw new IllegalStateException("directory listing==null");
    }

}
