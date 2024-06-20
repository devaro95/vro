import os

template = """
import os


def generate_view_model(screen_name):
    class_name = f"{screen_name}ViewModel"
    content  = f\"\"\"
package com.presentation.ui.{screen_name.lower()}

import com.presentation.ui.base.BaseViewModel
import com.presentation.ui.{screen_name.lower()}.{screen_name}Navigator.{screen_name}Destinations

class {class_name} : BaseViewModel<{screen_name}State, {screen_name}Destinations, {screen_name}Events>() {{
   
    override val initialState = {screen_name}State.INITIAL
    
    override fun eventListener(event: {screen_name}Events) {{
       
    }}
}}
    \"\"\".strip()
    write_file(f\"{class_name}.kt\", content, screen_name)


def generate_state(screen_name):
    class_name = f"{screen_name}State"
    content = f\"\"\"
package com.presentation.ui.{screen_name.lower()}
    
import com.vro.constants.EMPTY_STRING
import com.vro.state.VROState

data class {class_name}(
    val example: String = ""
) : VROState {{
    companion object {{
        val INITIAL = {class_name}(example = EMPTY_STRING)
    }}
}}
    \"\"\".strip()
    write_file(f\"{class_name}.kt\", content, screen_name)


def generate_events(screen_name):
    class_name = f"{screen_name}Events"
    content = f\"\"\"
package com.presentation.ui.{screen_name.lower()}
    
import com.vro.event.VROEvent

sealed class {class_name} : VROEvent {{

}}
    \"\"\".strip()
    write_file(f\"{class_name}.kt\", content, screen_name)


def generate_navigator(screen_name):
    class_name = f"{screen_name}Navigator"
    content = f\"\"\"
package com.presentation.ui.{screen_name.lower()}

import androidx.navigation.NavController
import com.vro.compose.VROComposableActivity
import com.vro.compose.VROComposableNavigator
import com.vro.navigation.VRODestination
import com.presentation.ui.{screen_name.lower()}.{screen_name}Navigator.{screen_name}Destinations

class {class_name}(
    activity: VROComposableActivity,
    navController: NavController,
) : VROComposableNavigator<{screen_name}Destinations>(activity, navController) {{

    override fun navigate(destination: {screen_name}Destinations) {{

    }}

    sealed class {screen_name}Destinations : VRODestination()
}}
    \"\"\".strip()
    write_file(f\"{class_name}.kt\", content, screen_name)


def generate_screen(screen_name):
    class_name = f"{screen_name}Screen"
    content = f\"\"\"
package com.presentation.ui.{screen_name.lower()}

import androidx.compose.runtime.Composable
import com.presentation.ui.base.BaseScreen
import com.vro.compose.VROSection
import com.vro.compose.preview.VROLightMultiDevicePreview

class {class_name} : BaseScreen<{screen_name}State, {screen_name}Events>() {{
  
    @Composable
    override fun composableContent() = listOf<VROSection<{screen_name}State, {screen_name}Events>>()
    
    @VROLightMultiDevicePreview
    @Composable
    override fun ComposablePreview() {{
        CreatePreview()
    }}
}}
    \"\"\".strip()
    write_file(f\"{class_name}.kt\", content, screen_name)


def generate_main_section(screen_name):
    class_name = f"{screen_name}MainSection"
    content = f\"\"\"
package com.presentation.ui.{screen_name.lower()}.sections

import androidx.compose.runtime.Composable
import com.presentation.ui.test.{screen_name}Events
import com.presentation.ui.test.{screen_name}State
import com.vro.compose.VROSection
import com.vro.compose.preview.VROLightMultiDevicePreview

class {class_name} : VROSection<{screen_name}State, {screen_name}Events>() {{
  
    @VROLightMultiDevicePreview
    @Composable
    override fun CreatePreview() {{
        CreateSection({screen_name}State.INITIAL)
    }}
    
    @Composable
    override fun CreateSection(state: {screen_name}State) {{
    
    }}
}}
    \"\"\".strip()
    write_section_file(f\"{class_name}.kt\", content, screen_name)


def write_file(file_name, content, package_name):
    directory = "{{{{presentation_package}}}}" + package_name.replace('.', '/').lower()
    if not os.path.exists(directory):
        os.makedirs(directory)
    file_path = os.path.join(directory, file_name)
    with open(file_path, "w") as file:
        file.write(content)
    print(f"Generated {file_path}")


def write_section_file(file_name, content, package_name):
    directory = "{{{{presentation_package}}}}" + package_name.replace('.', '/').lower() + "/sections"
    if not os.path.exists(directory):
        os.makedirs(directory)
    file_path = os.path.join(directory, file_name)
    with open(file_path, "w") as file:
        file.write(content)
    print(f"Generated {file_path}")


def main():
    screen_name = input("Introduce el nombre de la pantalla: ")

    generate_view_model(screen_name)
    generate_state(screen_name)
    generate_events(screen_name)
    generate_navigator(screen_name)
    generate_screen(screen_name)
    generate_main_section(screen_name)


if __name__ == "__main__":
    main()
"""


def generate_script(presentation_package):
    script_content = template.replace('{{{{presentation_package}}}}', f'{presentation_package.lower()}')

    script_filename = f'buildFeature.py'
    with open(script_filename, 'w') as file:
        file.write(script_content)
    os.chmod(script_filename, 0o755)
    print(f'Script {script_filename} has been created and is executable.')


def main():
    presentation_package = input("Introduce el nombre del packete: ")

    generate_script(presentation_package + "/")


if __name__ == "__main__":
    main()
