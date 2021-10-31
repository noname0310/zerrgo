package graphics.opengl;

import core.ZerrgoEngine;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL46;

import java.io.*;
import java.lang.ref.Cleaner;

public final class Shader implements core.graphics.resource.Shader {
    private static final Cleaner CLEANER = Cleaner.create();

    private String name;
    private int programId;
    private int vertexShaderId;
    private int fragmentShaderId;

    private record CleanerRunnable(
            AssetDisposer assetDisposer,
            int programId,
            int vertexShaderId,
            int fragmentShaderId
    ) implements Runnable {
        @Override
        public void run() {
            assetDisposer.addDisposeDelegate(() -> {
                ZerrgoEngine.Logger().info("disposing shader (program id: " + programId + ")");
                GL46.glUseProgram(0);
                GL46.glDetachShader(programId, vertexShaderId);
                GL46.glDetachShader(programId, fragmentShaderId);
                GL46.glDeleteShader(vertexShaderId);
                GL46.glDeleteShader(fragmentShaderId);
                GL46.glDeleteProgram(programId);
            });
        }
    }

    Shader(AssetDisposer assetDisposer, String vertexShaderFile, String fragmentShaderFile) {
        ZerrgoEngine.Logger().info(
                "Creating shader from \"" + vertexShaderFile +
                        "\" and \"" + fragmentShaderFile + "\".");

        name = vertexShaderFile + "|" + fragmentShaderFile;

        // 1. create two type of shader from txt file.
        vertexShaderId = loadShaderFromFile(vertexShaderFile, GL46.GL_VERTEX_SHADER);
        fragmentShaderId = loadShaderFromFile(fragmentShaderFile, GL46.GL_FRAGMENT_SHADER);

        // 2. create program and attach shader to program.
        programId = GL46.glCreateProgram();

        GL46.glAttachShader(programId, vertexShaderId);
        GL46.glAttachShader(programId, fragmentShaderId);

        GL46.glLinkProgram(programId);
        GL46.glValidateProgram(programId);

        // 3. delete shader -> no more need.
        GL46.glDeleteShader(vertexShaderId);
        GL46.glDeleteShader(fragmentShaderId);

        var cleanerRunnable = new CleanerRunnable(assetDisposer, programId, vertexShaderId, fragmentShaderId);
        CLEANER.register(this, cleanerRunnable);
    }

    private static int loadShaderFromFile(String fileName, int type) {
        StringBuilder shaderSource = new StringBuilder();

        // read each line and store source in string.
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            ZerrgoEngine.Logger().severe("Could not read file!");
            var stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            ZerrgoEngine.Logger().severe(stringWriter.toString());
            System.exit(-1);
        }

        // create particular type of shader.
        int shaderID = GL46.glCreateShader(type);

        // attach source
        GL46.glShaderSource(shaderID, shaderSource);

        // check compile
        GL46.glCompileShader(shaderID);
        if (GL46.glGetShaderi(shaderID, GL46.GL_COMPILE_STATUS) == GL46.GL_FALSE) {
            ZerrgoEngine.Logger().severe(GL46.glGetShaderInfoLog(shaderID, 500));
            ZerrgoEngine.Logger().severe("Could not compile shader.");
            System.exit(-1);
        }

        return shaderID;
    }

    void use() { GL46.glUseProgram(programId); }

    void unUse() { GL46.glUseProgram(0); }

    // register uniform variable in shader code.
    public void setFloat(String name, float value) {
        int loc = GL46.glGetUniformLocation(programId, name);
        GL46.glUniform1f(loc, value);
    }

    public void setInteger(String name, int value) {
        int loc = GL46.glGetUniformLocation(programId, name);
        GL46.glUniform1i(loc, value);
    }

    public void setInteger(String name, boolean value) {
        int loc = GL46.glGetUniformLocation(programId, name);
        GL46.glUniform1i(loc, value ? 1 : 0);
    }

    public void setVector2f(String name, float x, float y) {
        int loc = GL46.glGetUniformLocation(programId, name);
        GL46.glUniform2f(loc, x, y);
    }

    public void setVector2f(String name, Vector2f vec) {
        int loc = GL46.glGetUniformLocation(programId, name);
        GL46.glUniform2f(loc, vec.x, vec.y);
    }

    public void setVector3f(String name, Vector3f vec) {
        int loc = GL46.glGetUniformLocation(programId, name);
        GL46.glUniform3f(loc, vec.x, vec.y, vec.z);
    }

    public void setVector4f(String name, Vector4f vec) {
        int loc = GL46.glGetUniformLocation(programId, name);
        GL46.glUniform4f(loc, vec.x, vec.y, vec.z, vec.w);
    }

    /* Array to hand over the matrix */
    private final float[] matrix4fArr = new float[16];

    public void setMatrix4f(String name, Matrix4f matrix) {
        int loc = GL46.glGetUniformLocation(programId, name);

        matrix.get(matrix4fArr);
        GL46.glUniformMatrix4fv(loc, false, matrix4fArr);
    }

    @Override
    public String getName() { return name; }
}
