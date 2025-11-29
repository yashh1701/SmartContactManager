console.log("Script Loaded");

let currentTheme = getTheme();

// Apply the theme when content loads
document.addEventListener("DOMContentLoaded", () => {
    applyTheme(currentTheme);
    setThemeButtonText(currentTheme);
    attachThemeChangeListener();
});

// Function to apply theme
function applyTheme(theme) {
    const htmlElement = document.documentElement; // Use document.documentElement to access the <html> element
    htmlElement.classList.remove("light", "dark"); // Remove any previous theme
    htmlElement.classList.add(theme); // Apply the new theme
}

// Function to update button text
function setThemeButtonText(theme) {
    const changeThemeButton = document.querySelector('#theme_change_button span');
    if (changeThemeButton) {
        changeThemeButton.textContent = theme === "light" ? "Dark" : "Light";
    }
}

// Function to handle theme toggle on button click
function attachThemeChangeListener() {
    const changeThemeButton = document.querySelector('#theme_change_button');
    if (!changeThemeButton) {
        console.error("Theme change button not found!");
        return;
    }

    changeThemeButton.addEventListener('click', () => {
        // Toggle theme
        currentTheme = currentTheme === "dark" ? "light" : "dark";

        // Apply theme
        applyTheme(currentTheme);

        // Update local storage
        setTheme(currentTheme);

        // Update button text
        setThemeButtonText(currentTheme);
    });
}

// Set theme to local storage
function setTheme(theme) {
    localStorage.setItem("theme", theme);
}

// Get theme from local storage
function getTheme() {
    let theme = localStorage.getItem("theme");
    return theme ? theme : "light"; // Default to 'light' if no theme is set
}