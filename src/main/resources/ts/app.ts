import 'css/app.css';


/**
 * Register an event at the document for the specified selector,
 * so events are still catched after DOM changes.
 */
function handleEvent(eventType: string, selector: string, handler: (event:Event) => any) {
  document.addEventListener(eventType, function(event) {
    const eventTarget = event.target! as Element;
    if (eventTarget.matches(selector + ', ' + selector + ' *')) {
      handler.apply(eventTarget.closest(selector), arguments);
    }
  });
}

handleEvent('submit', '.js-submit-confirm', function(event: Event) {
  if (!confirm(this.getAttribute('data-confirm-message'))) {
    event.preventDefault();
    return false;
  }
  return true;
});

handleEvent('click', 'body', function(event: Event) {
  // close any open dropdown
  const $clickedDropdown = (event.target! as Element).closest('.js-dropdown');
  const $dropdowns = document.querySelectorAll('.js-dropdown');
  $dropdowns.forEach(($dropdown) => {
    if ($clickedDropdown !== $dropdown && $dropdown.getAttribute('data-dropdown-keepopen') !== 'true') {
      $dropdown.ariaExpanded = 'false';
      $dropdown.nextElementSibling!.classList.add('hidden');
    }
  });
  // toggle selected if applicable
  if ($clickedDropdown) {
    $clickedDropdown.ariaExpanded = '' + ($clickedDropdown.ariaExpanded !== 'true');
    $clickedDropdown.nextElementSibling!.classList.toggle('hidden');
    event.preventDefault();
  }
});

/**
 * active menu item on page load based on current URL
 */
document.addEventListener('DOMContentLoaded', function() {
  // get first part of URL, e.g. /admin/... -> /admin
  const currentUrl = window.location.pathname.split('/').slice(0, 2).join('/');
  const $navLinks = document.querySelectorAll('.js-nav-link');
  $navLinks.forEach(($navLink) => {
    console.log($navLink.getAttribute('href'));
    if ($navLink.getAttribute('href') === currentUrl) {
      $navLink.classList.add('bg-gray-700');
      $navLink.classList.add('text-white');
      $navLink.classList.remove('text-gray-400');
    }
  });
});
