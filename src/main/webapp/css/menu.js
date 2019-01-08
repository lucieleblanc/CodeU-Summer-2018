function toggleMenu() {
    var menuToggle = document.getElementsByClassName("navItems");
    for (var i = 0; i < menuToggle.length; i++) {
      menuToggle[i].classList.toggle("hidden");
    }
}

function checkScreenSize() {
  var menuToggle = document.getElementsByClassName("navItems");
  for (var i = 0; i < menuToggle.length; i++) {
    menuToggle[i].classList.remove("hidden");
  }
}
