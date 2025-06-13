document.addEventListener('DOMContentLoaded', () => {
    const elements = document.querySelectorAll('.fade-in');

    console.log("Elementos encontrados para fade-in:", elements.length);

    if ('IntersectionObserver' in window) {
        const observer = new IntersectionObserver((entries, obs) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    console.log("Elemento visível:", entry.target);
                    entry.target.classList.add('visible');
                    obs.unobserve(entry.target); // Para não animar de novo
                }
            });
        }, { threshold: 0.1 });

        elements.forEach(el => observer.observe(el));
    } else {
        console.warn("IntersectionObserver não suportado, mostrando tudo diretamente.");
        elements.forEach(el => el.classList.add('visible'));
    }

    // Scroll suave para cliques em âncoras da navbar
    document.querySelectorAll('a[href^="#"]').forEach(link => {
        link.addEventListener('click', function (e) {
            e.preventDefault();
            const id = this.getAttribute('href').substring(1);
            const target = document.getElementById(id);
            if (target) {
                target.scrollIntoView({ behavior: 'smooth' });
            }
        });
    });
});
