# Issue with KAPT and openapi from Micronaut 4.4.0
Link to the issue: [https://github.com/micronaut-projects/micronaut-platform/issues/1560](https://github.com/micronaut-projects/micronaut-platform/issues/1560)

## Introduction

In the repository I'm showing an issue happening from `micronautVersion=4.4.0`.
openapi file is not generated when using `kapt`. It works when using `ksp`.

It looks like properties defined under `openapi.properties` (i.e `micronaut.openapi.target.file`) are being ignored.

## Steps to reproduce
```
./gradlew clean build
```

### Expected behavior
`openapi-definition.yml` file should be generated

### Using ksp solves the issue 

Setting ksp over the openapi dependency fixes the issue:

From `build.gradle.kts`: 
```
kapt("io.micronaut.openapi:micronaut-openapi")
```
to
```
ksp("io.micronaut.openapi:micronaut-openapi")
```

### Works when using micronaut < 4.4.0
Setting `micronautVersion` to `4.3.0` in `gradle.properties` (and keeping openapi in kapt in the `build.gradle` file)
shows how it was working before.

## Stacktrace: 
```
test-micronaut-4.5-openapi/openapi $ ./gradlew clean build

> Task :kaptKotlin FAILED
Note: Generating OpenAPI Documentation
warning: Can't set some placeholders in fileName: ${openapi.title}-${api.version}.yml
warning: Error:
  Invalid relative name: META-INF/swagger/${openapi.title}-${api.version}.yml
  java.lang.IllegalArgumentException: Invalid relative name: META-INF/swagger/${openapi.title}-${api.version}.yml
        at jdk.compiler/com.sun.tools.javac.file.JavacFileManager.getFileForOutput(JavacFileManager.java:898)
        at org.jetbrains.kotlin.kapt3.base.javac.KaptJavaFileManager.getFileForOutput(KaptJavaFileManager.kt:82)
        at jdk.compiler/com.sun.tools.javac.processing.JavacFiler.createResource(JavacFiler.java:541)
        at org.jetbrains.kotlin.kapt3.base.incremental.IncrementalFiler.createResource(incrementalProcessors.kt:121)
        at io.micronaut.annotation.processing.AnnotationProcessingOutputVisitor$GeneratedFileObject.getOutputObject(AnnotationProcessingOutputVisitor.java:288)
        at io.micronaut.annotation.processing.AnnotationProcessingOutputVisitor$GeneratedFileObject.toURI(AnnotationProcessingOutputVisitor.java:221)
        at io.micronaut.openapi.visitor.ContextUtils.visitMetaInfFile(ContextUtils.java:98)
        at io.micronaut.openapi.visitor.FileUtils.getDefaultFilePath(FileUtils.java:95)
        at io.micronaut.openapi.visitor.FileUtils.openApiSpecFile(FileUtils.java:114)
        at io.micronaut.openapi.visitor.OpenApiApplicationVisitor.writeYamlToFile(OpenApiApplicationVisitor.java:774)
        at io.micronaut.openapi.visitor.OpenApiApplicationVisitor.finish(OpenApiApplicationVisitor.java:484)
        at io.micronaut.annotation.processing.TypeElementVisitorProcessor.process(TypeElementVisitorProcessor.java:281)
        at org.jetbrains.kotlin.kapt3.base.incremental.IncrementalProcessor.process(incrementalProcessors.kt:90)
        at org.jetbrains.kotlin.kapt3.base.ProcessorWrapper.process(annotationProcessing.kt:209)
        at jdk.compiler/com.sun.tools.javac.processing.JavacProcessingEnvironment.callProcessor(JavacProcessingEnvironment.java:1023)
        at jdk.compiler/com.sun.tools.javac.processing.JavacProcessingEnvironment.discoverAndRunProcs(JavacProcessingEnvironment.java:939)
        at jdk.compiler/com.sun.tools.javac.processing.JavacProcessingEnvironment$Round.run(JavacProcessingEnvironment.java:1267)
        at jdk.compiler/com.sun.tools.javac.processing.JavacProcessingEnvironment.doProcessing(JavacProcessingEnvironment.java:1382)
        at jdk.compiler/com.sun.tools.javac.main.JavaCompiler.processAnnotations(JavaCompiler.java:1234)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:568)
        at org.jetbrains.kotlin.kapt3.base.AnnotationProcessingKt.doAnnotationProcessing(annotationProcessing.kt:92)
        at org.jetbrains.kotlin.kapt3.base.AnnotationProcessingKt.doAnnotationProcessing$default(annotationProcessing.kt:33)
        at org.jetbrains.kotlin.kapt3.base.Kapt.kapt(Kapt.kt:47)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:568)
        at org.jetbrains.kotlin.gradle.internal.KaptExecution.run(KaptWithoutKotlincTask.kt:320)
        at org.jetbrains.kotlin.gradle.internal.KaptWithoutKotlincTask$KaptExecutionWorkAction.execute(KaptWithoutKotlincTask.kt:266)
        at org.gradle.workers.internal.DefaultWorkerServer.execute(DefaultWorkerServer.java:63)
        at org.gradle.workers.internal.AbstractClassLoaderWorker$1.create(AbstractClassLoaderWorker.java:54)
        at org.gradle.workers.internal.AbstractClassLoaderWorker$1.create(AbstractClassLoaderWorker.java:48)
        at org.gradle.internal.classloader.ClassLoaderUtils.executeInClassloader(ClassLoaderUtils.java:100)
        at org.gradle.workers.internal.AbstractClassLoaderWorker.executeInClassLoader(AbstractClassLoaderWorker.java:48)
        at org.gradle.workers.internal.IsolatedClassloaderWorker.run(IsolatedClassloaderWorker.java:49)
        at org.gradle.workers.internal.IsolatedClassloaderWorker.run(IsolatedClassloaderWorker.java:30)
        at org.gradle.workers.internal.WorkerDaemonServer.run(WorkerDaemonServer.java:102)
        at org.gradle.workers.internal.WorkerDaemonServer.run(WorkerDaemonServer.java:71)
        at org.gradle.process.internal.worker.request.WorkerAction$1.call(WorkerAction.java:146)
        at org.gradle.process.internal.worker.child.WorkerLogEventListener.withWorkerLoggingProtocol(WorkerLogEventListener.java:41)
        at org.gradle.process.internal.worker.request.WorkerAction.lambda$run$0(WorkerAction.java:143)
        at org.gradle.internal.operations.CurrentBuildOperationRef.with(CurrentBuildOperationRef.java:80)
        at org.gradle.process.internal.worker.request.WorkerAction.run(WorkerAction.java:135)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:568)
        at org.gradle.internal.dispatch.ReflectionDispatch.dispatch(ReflectionDispatch.java:36)
        at org.gradle.internal.dispatch.ReflectionDispatch.dispatch(ReflectionDispatch.java:24)
        at org.gradle.internal.remote.internal.hub.MessageHubBackedObjectConnection$DispatchWrapper.dispatch(MessageHubBackedObjectConnection.java:182)
        at org.gradle.internal.remote.internal.hub.MessageHubBackedObjectConnection$DispatchWrapper.dispatch(MessageHubBackedObjectConnection.java:164)
        at org.gradle.internal.remote.internal.hub.MessageHub$Handler.run(MessageHub.java:414)
        at org.gradle.internal.concurrent.ExecutorPolicy$CatchAndRecordFailures.onExecute(ExecutorPolicy.java:64)
        at org.gradle.internal.concurrent.AbstractManagedExecutor$1.run(AbstractManagedExecutor.java:47)
        at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
        at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
        at java.base/java.lang.Thread.run(Thread.java:833)
error: Error finalizing type visitor [io.micronaut.openapi.visitor.OpenApiApplicationVisitor@407e6e29]: Invalid relative name: META-INF/swagger/${openapi.title}-${api.version}.yml
  java.lang.IllegalArgumentException: Invalid relative name: META-INF/swagger/${openapi.title}-${api.version}.yml
        at jdk.compiler/com.sun.tools.javac.file.JavacFileManager.getFileForOutput(JavacFileManager.java:898)
        at org.jetbrains.kotlin.kapt3.base.javac.KaptJavaFileManager.getFileForOutput(KaptJavaFileManager.kt:82)
        at jdk.compiler/com.sun.tools.javac.processing.JavacFiler.createResource(JavacFiler.java:541)
        at org.jetbrains.kotlin.kapt3.base.incremental.IncrementalFiler.createResource(incrementalProcessors.kt:121)
        at io.micronaut.annotation.processing.AnnotationProcessingOutputVisitor$GeneratedFileObject.getOutputObject(AnnotationProcessingOutputVisitor.java:288)
        at io.micronaut.annotation.processing.AnnotationProcessingOutputVisitor$GeneratedFileObject.toURI(AnnotationProcessingOutputVisitor.java:221)
        at io.micronaut.openapi.visitor.ContextUtils.visitMetaInfFile(ContextUtils.java:98)
        at io.micronaut.openapi.visitor.FileUtils.getDefaultFilePath(FileUtils.java:95)
        at io.micronaut.openapi.visitor.FileUtils.openApiSpecFile(FileUtils.java:114)
        at io.micronaut.openapi.visitor.OpenApiApplicationVisitor.writeYamlToFile(OpenApiApplicationVisitor.java:774)
        at io.micronaut.openapi.visitor.OpenApiApplicationVisitor.finish(OpenApiApplicationVisitor.java:484)
        at io.micronaut.annotation.processing.TypeElementVisitorProcessor.process(TypeElementVisitorProcessor.java:281)
        at org.jetbrains.kotlin.kapt3.base.incremental.IncrementalProcessor.process(incrementalProcessors.kt:90)
        at org.jetbrains.kotlin.kapt3.base.ProcessorWrapper.process(annotationProcessing.kt:209)
        at jdk.compiler/com.sun.tools.javac.processing.JavacProcessingEnvironment.callProcessor(JavacProcessingEnvironment.java:1023)
        at jdk.compiler/com.sun.tools.javac.processing.JavacProcessingEnvironment.discoverAndRunProcs(JavacProcessingEnvironment.java:939)
        at jdk.compiler/com.sun.tools.javac.processing.JavacProcessingEnvironment$Round.run(JavacProcessingEnvironment.java:1267)
        at jdk.compiler/com.sun.tools.javac.processing.JavacProcessingEnvironment.doProcessing(JavacProcessingEnvironment.java:1382)
        at jdk.compiler/com.sun.tools.javac.main.JavaCompiler.processAnnotations(JavaCompiler.java:1234)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:568)
        at org.jetbrains.kotlin.kapt3.base.AnnotationProcessingKt.doAnnotationProcessing(annotationProcessing.kt:92)
        at org.jetbrains.kotlin.kapt3.base.AnnotationProcessingKt.doAnnotationProcessing$default(annotationProcessing.kt:33)
        at org.jetbrains.kotlin.kapt3.base.Kapt.kapt(Kapt.kt:47)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:568)
        at org.jetbrains.kotlin.gradle.internal.KaptExecution.run(KaptWithoutKotlincTask.kt:320)
        at org.jetbrains.kotlin.gradle.internal.KaptWithoutKotlincTask$KaptExecutionWorkAction.execute(KaptWithoutKotlincTask.kt:266)
        at org.gradle.workers.internal.DefaultWorkerServer.execute(DefaultWorkerServer.java:63)
        at org.gradle.workers.internal.AbstractClassLoaderWorker$1.create(AbstractClassLoaderWorker.java:54)
        at org.gradle.workers.internal.AbstractClassLoaderWorker$1.create(AbstractClassLoaderWorker.java:48)
        at org.gradle.internal.classloader.ClassLoaderUtils.executeInClassloader(ClassLoaderUtils.java:100)
        at org.gradle.workers.internal.AbstractClassLoaderWorker.executeInClassLoader(AbstractClassLoaderWorker.java:48)
        at org.gradle.workers.internal.IsolatedClassloaderWorker.run(IsolatedClassloaderWorker.java:49)
        at org.gradle.workers.internal.IsolatedClassloaderWorker.run(IsolatedClassloaderWorker.java:30)
        at org.gradle.workers.internal.WorkerDaemonServer.run(WorkerDaemonServer.java:102)
        at org.gradle.workers.internal.WorkerDaemonServer.run(WorkerDaemonServer.java:71)
        at org.gradle.process.internal.worker.request.WorkerAction$1.call(WorkerAction.java:146)
        at org.gradle.process.internal.worker.child.WorkerLogEventListener.withWorkerLoggingProtocol(WorkerLogEventListener.java:41)
        at org.gradle.process.internal.worker.request.WorkerAction.lambda$run$0(WorkerAction.java:143)
        at org.gradle.internal.operations.CurrentBuildOperationRef.with(CurrentBuildOperationRef.java:80)
        at org.gradle.process.internal.worker.request.WorkerAction.run(WorkerAction.java:135)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:568)
        at org.gradle.internal.dispatch.ReflectionDispatch.dispatch(ReflectionDispatch.java:36)
        at org.gradle.internal.dispatch.ReflectionDispatch.dispatch(ReflectionDispatch.java:24)
        at org.gradle.internal.remote.internal.hub.MessageHubBackedObjectConnection$DispatchWrapper.dispatch(MessageHubBackedObjectConnection.java:182)
        at org.gradle.internal.remote.internal.hub.MessageHubBackedObjectConnection$DispatchWrapper.dispatch(MessageHubBackedObjectConnection.java:164)
        at org.gradle.internal.remote.internal.hub.MessageHub$Handler.run(MessageHub.java:414)
        at org.gradle.internal.concurrent.ExecutorPolicy$CatchAndRecordFailures.onExecute(ExecutorPolicy.java:64)
        at org.gradle.internal.concurrent.AbstractManagedExecutor$1.run(AbstractManagedExecutor.java:47)
        at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
        at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
        at java.base/java.lang.Thread.run(Thread.java:833)
warning: Can't set some placeholders in fileName: ${openapi.title}-${api.version}.yml
warning: Error:
  Invalid relative name: META-INF/swagger/${openapi.title}-${api.version}.yml
  java.lang.IllegalArgumentException: Invalid relative name: META-INF/swagger/${openapi.title}-${api.version}.yml
        at jdk.compiler/com.sun.tools.javac.file.JavacFileManager.getFileForOutput(JavacFileManager.java:898)
        at org.jetbrains.kotlin.kapt3.base.javac.KaptJavaFileManager.getFileForOutput(KaptJavaFileManager.kt:82)
        at jdk.compiler/com.sun.tools.javac.processing.JavacFiler.createResource(JavacFiler.java:541)
        at org.jetbrains.kotlin.kapt3.base.incremental.IncrementalFiler.createResource(incrementalProcessors.kt:121)
        at io.micronaut.annotation.processing.AnnotationProcessingOutputVisitor$GeneratedFileObject.getOutputObject(AnnotationProcessingOutputVisitor.java:288)
        at io.micronaut.annotation.processing.AnnotationProcessingOutputVisitor$GeneratedFileObject.toURI(AnnotationProcessingOutputVisitor.java:221)
        at io.micronaut.openapi.visitor.FileUtils.getDefaultFilePath(FileUtils.java:97)
        at io.micronaut.openapi.visitor.FileUtils.openApiSpecFile(FileUtils.java:114)
        at io.micronaut.openapi.visitor.OpenApiApplicationVisitor.writeYamlToFile(OpenApiApplicationVisitor.java:774)
        at io.micronaut.openapi.visitor.OpenApiApplicationVisitor.finish(OpenApiApplicationVisitor.java:484)
        at io.micronaut.annotation.processing.TypeElementVisitorProcessor.process(TypeElementVisitorProcessor.java:281)
        at org.jetbrains.kotlin.kapt3.base.incremental.IncrementalProcessor.process(incrementalProcessors.kt:90)
        at org.jetbrains.kotlin.kapt3.base.ProcessorWrapper.process(annotationProcessing.kt:209)
        at jdk.compiler/com.sun.tools.javac.processing.JavacProcessingEnvironment.callProcessor(JavacProcessingEnvironment.java:1023)
        at jdk.compiler/com.sun.tools.javac.processing.JavacProcessingEnvironment$DiscoveredProcessors$ProcessorStateIterator.runContributingProcs(JavacProcessingEnvironment.java:859)
        at jdk.compiler/com.sun.tools.javac.processing.JavacProcessingEnvironment$Round.run(JavacProcessingEnvironment.java:1265)
        at jdk.compiler/com.sun.tools.javac.processing.JavacProcessingEnvironment.doProcessing(JavacProcessingEnvironment.java:1404)
        at jdk.compiler/com.sun.tools.javac.main.JavaCompiler.processAnnotations(JavaCompiler.java:1234)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:568)
        at org.jetbrains.kotlin.kapt3.base.AnnotationProcessingKt.doAnnotationProcessing(annotationProcessing.kt:92)
        at org.jetbrains.kotlin.kapt3.base.AnnotationProcessingKt.doAnnotationProcessing$default(annotationProcessing.kt:33)
        at org.jetbrains.kotlin.kapt3.base.Kapt.kapt(Kapt.kt:47)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:568)
        at org.jetbrains.kotlin.gradle.internal.KaptExecution.run(KaptWithoutKotlincTask.kt:320)
        at org.jetbrains.kotlin.gradle.internal.KaptWithoutKotlincTask$KaptExecutionWorkAction.execute(KaptWithoutKotlincTask.kt:266)
        at org.gradle.workers.internal.DefaultWorkerServer.execute(DefaultWorkerServer.java:63)
        at org.gradle.workers.internal.AbstractClassLoaderWorker$1.create(AbstractClassLoaderWorker.java:54)
        at org.gradle.workers.internal.AbstractClassLoaderWorker$1.create(AbstractClassLoaderWorker.java:48)
        at org.gradle.internal.classloader.ClassLoaderUtils.executeInClassloader(ClassLoaderUtils.java:100)
        at org.gradle.workers.internal.AbstractClassLoaderWorker.executeInClassLoader(AbstractClassLoaderWorker.java:48)
        at org.gradle.workers.internal.IsolatedClassloaderWorker.run(IsolatedClassloaderWorker.java:49)
        at org.gradle.workers.internal.IsolatedClassloaderWorker.run(IsolatedClassloaderWorker.java:30)
        at org.gradle.workers.internal.WorkerDaemonServer.run(WorkerDaemonServer.java:102)
        at org.gradle.workers.internal.WorkerDaemonServer.run(WorkerDaemonServer.java:71)
        at org.gradle.process.internal.worker.request.WorkerAction$1.call(WorkerAction.java:146)
        at org.gradle.process.internal.worker.child.WorkerLogEventListener.withWorkerLoggingProtocol(WorkerLogEventListener.java:41)
        at org.gradle.process.internal.worker.request.WorkerAction.lambda$run$0(WorkerAction.java:143)
        at org.gradle.internal.operations.CurrentBuildOperationRef.with(CurrentBuildOperationRef.java:80)
        at org.gradle.process.internal.worker.request.WorkerAction.run(WorkerAction.java:135)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:568)
        at org.gradle.internal.dispatch.ReflectionDispatch.dispatch(ReflectionDispatch.java:36)
        at org.gradle.internal.dispatch.ReflectionDispatch.dispatch(ReflectionDispatch.java:24)
        at org.gradle.internal.remote.internal.hub.MessageHubBackedObjectConnection$DispatchWrapper.dispatch(MessageHubBackedObjectConnection.java:182)
        at org.gradle.internal.remote.internal.hub.MessageHubBackedObjectConnection$DispatchWrapper.dispatch(MessageHubBackedObjectConnection.java:164)
        at org.gradle.internal.remote.internal.hub.MessageHub$Handler.run(MessageHub.java:414)
        at org.gradle.internal.concurrent.ExecutorPolicy$CatchAndRecordFailures.onExecute(ExecutorPolicy.java:64)
        at org.gradle.internal.concurrent.AbstractManagedExecutor$1.run(AbstractManagedExecutor.java:47)
        at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
        at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
        at java.base/java.lang.Thread.run(Thread.java:833)
error: Error finalizing type visitor [io.micronaut.openapi.visitor.OpenApiApplicationVisitor@407e6e29]: Invalid relative name: META-INF/swagger/${openapi.title}-${api.version}.yml
  java.lang.IllegalArgumentException: Invalid relative name: META-INF/swagger/${openapi.title}-${api.version}.yml
        at jdk.compiler/com.sun.tools.javac.file.JavacFileManager.getFileForOutput(JavacFileManager.java:898)
        at org.jetbrains.kotlin.kapt3.base.javac.KaptJavaFileManager.getFileForOutput(KaptJavaFileManager.kt:82)
        at jdk.compiler/com.sun.tools.javac.processing.JavacFiler.createResource(JavacFiler.java:541)
        at org.jetbrains.kotlin.kapt3.base.incremental.IncrementalFiler.createResource(incrementalProcessors.kt:121)
        at io.micronaut.annotation.processing.AnnotationProcessingOutputVisitor$GeneratedFileObject.getOutputObject(AnnotationProcessingOutputVisitor.java:288)
        at io.micronaut.annotation.processing.AnnotationProcessingOutputVisitor$GeneratedFileObject.toURI(AnnotationProcessingOutputVisitor.java:221)
        at io.micronaut.openapi.visitor.FileUtils.getDefaultFilePath(FileUtils.java:97)
        at io.micronaut.openapi.visitor.FileUtils.openApiSpecFile(FileUtils.java:114)
        at io.micronaut.openapi.visitor.OpenApiApplicationVisitor.writeYamlToFile(OpenApiApplicationVisitor.java:774)
        at io.micronaut.openapi.visitor.OpenApiApplicationVisitor.finish(OpenApiApplicationVisitor.java:484)
        at io.micronaut.annotation.processing.TypeElementVisitorProcessor.process(TypeElementVisitorProcessor.java:281)
        at org.jetbrains.kotlin.kapt3.base.incremental.IncrementalProcessor.process(incrementalProcessors.kt:90)
        at org.jetbrains.kotlin.kapt3.base.ProcessorWrapper.process(annotationProcessing.kt:209)
        at jdk.compiler/com.sun.tools.javac.processing.JavacProcessingEnvironment.callProcessor(JavacProcessingEnvironment.java:1023)
        at jdk.compiler/com.sun.tools.javac.processing.JavacProcessingEnvironment$DiscoveredProcessors$ProcessorStateIterator.runContributingProcs(JavacProcessingEnvironment.java:859)
        at jdk.compiler/com.sun.tools.javac.processing.JavacProcessingEnvironment$Round.run(JavacProcessingEnvironment.java:1265)
        at jdk.compiler/com.sun.tools.javac.processing.JavacProcessingEnvironment.doProcessing(JavacProcessingEnvironment.java:1404)
        at jdk.compiler/com.sun.tools.javac.main.JavaCompiler.processAnnotations(JavaCompiler.java:1234)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:568)
        at org.jetbrains.kotlin.kapt3.base.AnnotationProcessingKt.doAnnotationProcessing(annotationProcessing.kt:92)
        at org.jetbrains.kotlin.kapt3.base.AnnotationProcessingKt.doAnnotationProcessing$default(annotationProcessing.kt:33)
        at org.jetbrains.kotlin.kapt3.base.Kapt.kapt(Kapt.kt:47)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:568)
        at org.jetbrains.kotlin.gradle.internal.KaptExecution.run(KaptWithoutKotlincTask.kt:320)
        at org.jetbrains.kotlin.gradle.internal.KaptWithoutKotlincTask$KaptExecutionWorkAction.execute(KaptWithoutKotlincTask.kt:266)
        at org.gradle.workers.internal.DefaultWorkerServer.execute(DefaultWorkerServer.java:63)
        at org.gradle.workers.internal.AbstractClassLoaderWorker$1.create(AbstractClassLoaderWorker.java:54)
        at org.gradle.workers.internal.AbstractClassLoaderWorker$1.create(AbstractClassLoaderWorker.java:48)
        at org.gradle.internal.classloader.ClassLoaderUtils.executeInClassloader(ClassLoaderUtils.java:100)
        at org.gradle.workers.internal.AbstractClassLoaderWorker.executeInClassLoader(AbstractClassLoaderWorker.java:48)
        at org.gradle.workers.internal.IsolatedClassloaderWorker.run(IsolatedClassloaderWorker.java:49)
        at org.gradle.workers.internal.IsolatedClassloaderWorker.run(IsolatedClassloaderWorker.java:30)
        at org.gradle.workers.internal.WorkerDaemonServer.run(WorkerDaemonServer.java:102)
        at org.gradle.workers.internal.WorkerDaemonServer.run(WorkerDaemonServer.java:71)
        at org.gradle.process.internal.worker.request.WorkerAction$1.call(WorkerAction.java:146)
        at org.gradle.process.internal.worker.child.WorkerLogEventListener.withWorkerLoggingProtocol(WorkerLogEventListener.java:41)
        at org.gradle.process.internal.worker.request.WorkerAction.lambda$run$0(WorkerAction.java:143)
        at org.gradle.internal.operations.CurrentBuildOperationRef.with(CurrentBuildOperationRef.java:80)
        at org.gradle.process.internal.worker.request.WorkerAction.run(WorkerAction.java:135)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:568)
        at org.gradle.internal.dispatch.ReflectionDispatch.dispatch(ReflectionDispatch.java:36)
        at org.gradle.internal.dispatch.ReflectionDispatch.dispatch(ReflectionDispatch.java:24)
        at org.gradle.internal.remote.internal.hub.MessageHubBackedObjectConnection$DispatchWrapper.dispatch(MessageHubBackedObjectConnection.java:182)
        at org.gradle.internal.remote.internal.hub.MessageHubBackedObjectConnection$DispatchWrapper.dispatch(MessageHubBackedObjectConnection.java:164)
        at org.gradle.internal.remote.internal.hub.MessageHub$Handler.run(MessageHub.java:414)
        at org.gradle.internal.concurrent.ExecutorPolicy$CatchAndRecordFailures.onExecute(ExecutorPolicy.java:64)
        at org.gradle.internal.concurrent.AbstractManagedExecutor$1.run(AbstractManagedExecutor.java:47)
        at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
        at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
        at java.base/java.lang.Thread.run(Thread.java:833)

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':kaptKotlin'.
> A failure occurred while executing org.jetbrains.kotlin.gradle.internal.KaptWithoutKotlincTask$KaptExecutionWorkAction

* Try:
> Run with --stacktrace option to get the stack trace.
> Run with --info or --debug option to get more log output.
> Run with --scan to get full insights.
> Get more help at https://help.gradle.org.

Deprecated Gradle features were used in this build, making it incompatible with Gradle 9.0.

You can use '--warning-mode all' to show the individual deprecation warnings and determine if they come from your own scripts or plugins.

For more on this, please refer to https://docs.gradle.org/8.8/userguide/command_line_interface.html#sec:command_line_warnings in the Gradle documentation.

BUILD FAILED in 5s
9 actionable tasks: 9 executed
```

