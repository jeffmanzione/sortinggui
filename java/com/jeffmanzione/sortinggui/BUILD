package(default_visibility = ["//visibility:public"])

java_binary(
    name = "sortinggui",
    srcs = glob(["*.java"]),
    jvm_flags = [
        "--module-path=$(location @maven//:org_openjfx_javafx_controls_win);" +
        "$(location @maven//:org_openjfx_javafx_graphics_win);" +
        "$(location @maven//:org_openjfx_javafx_base_win)",
        "--add-modules=javafx.controls",
    ],
    main_class = "com.jeffmanzione.sortinggui.SortingApplication",
    deps = [
        "//java/com/jeffmanzione/sortinggui/graphics",
        "//java/com/jeffmanzione/sortinggui/graphics/exceptions",
        "//java/com/jeffmanzione/sortinggui/sorts",
        "//java/com/jeffmanzione/sortinggui/sorts/business",
        "@maven//:org_openjfx_javafx_base_win",
        "@maven//:org_openjfx_javafx_controls_win",
        "@maven//:org_openjfx_javafx_graphics_win",
    ],
)
