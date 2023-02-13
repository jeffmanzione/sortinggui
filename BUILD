package(default_visibility = ["//visibility:public"])

java_binary(
    name = "sortinggui",
    jvm_flags = [
        "--module-path=$(location @maven//:org_openjfx_javafx_controls_win)",
        "--add-modules=javafx.controls",
    ],
    main_class = "com.jeffmanzione.sortinggui.graphics.SortingApplication",
    runtime_deps = [
        "//java/com/jeffmanzione/sortinggui/graphics",
        "@maven//:org_openjfx_javafx_base_win",
        "@maven//:org_openjfx_javafx_controls_win",
        "@maven//:org_openjfx_javafx_graphics_win",
    ],
    # deps = [
    #     "@maven//:org_openjfx_javafx_controls_win",
    # ],
)
