const inputs = document.querySelectorAll('input.form-control');
inputs.forEach( input => updatePlaceholder(input));
inputs.forEach(t => t.addEventListener('input', e => updatePlaceholder(e.currentTarget)));

function updatePlaceholder(input) {
    const label = input.parentNode.querySelector('.form-control-placeholder');
    input.value.length !== 0 ? label.classList.add('d-none') : label.classList.remove('d-none');
}