document.addEventListener('DOMContentLoaded', function() {
    // Existing code for gradient and alerts
    const bloco = document.querySelector('.aulas-marcadas-scroll');
    if (bloco) {
        updateGradient();
        bloco.addEventListener('scroll', updateGradient);
        window.addEventListener('resize', updateGradient);
    }
    const alerta = document.querySelector('.alerta-sucesso, .alerta-erro');
    if (alerta) {
        setTimeout(() => {
            alerta.style.display = 'none';
        }, 2000);
    }

    // New code to check URL parameter and open modal with pre-selected modalidade
    const urlParams = new URLSearchParams(window.location.search);
    const modalidadeParam = urlParams.get('modalidade');
    if (modalidadeParam) {
        const modalidadeSelect = document.getElementById('modalidadeAula');
        if (modalidadeSelect) {
            modalidadeSelect.value = modalidadeParam;
        }
        abrirModalAgendamento();
    }
});

function updateGradient() {
    const bloco = document.querySelector('.aulas-marcadas-scroll');
    if (!bloco) return;
    if (bloco.scrollTop === 0 && bloco.scrollHeight > bloco.clientHeight) {
        bloco.classList.add('show-gradient');
    } else {
        bloco.classList.remove('show-gradient');
    }
}

function abrirDetalhes(modalidade, data, horario, instrutor, estudio, status, aulaId) {
    document.getElementById('detalheModalidade').textContent = modalidade;
    document.getElementById('detalheData').textContent = data;
    document.getElementById('detalheHorario').textContent = horario;
    // document.getElementById('detalheInstrutor').textContent = instrutor;
    document.getElementById('detalheEstudio').textContent = estudio;
    document.getElementById('detalheStatus').textContent = status;
    document.getElementById('detalheAulaId').value = aulaId;
    document.getElementById('modalDetalhes').style.display = 'flex';
}

function fecharDetalhes() {
    document.getElementById('modalDetalhes').style.display = 'none';
}

function abrirModalAgendamento() {
    document.getElementById('modalAgendamento').style.display = 'flex';
}

function fecharModalAgendamento() {
    document.getElementById('modalAgendamento').style.display = 'none';
}

window.onclick = function(event) {
    const modalDetalhes = document.getElementById('modalDetalhes');
    const modalAgendamento = document.getElementById('modalAgendamento');
    if (event.target === modalDetalhes) fecharDetalhes();
    if (event.target === modalAgendamento) fecharModalAgendamento();
}

function abrirReagendar() {
    document.getElementById('dataAula').value = document.getElementById('detalheData').textContent.split('/').reverse().join('-');
    document.getElementById('horarioAula').value = document.getElementById('detalheHorario').textContent;
    document.getElementById('formAgendamento').action = '/web/aula/reagendar/' + document.getElementById('detalheAulaId').value;
    fecharDetalhes();
    abrirModalAgendamento();
}

function desmarcarAula() {
    const aulaId = document.getElementById('detalheAulaId').value;
    if (!aulaId) return;
    if (!confirm('Tem certeza que deseja desmarcar esta aula?')) return;
    fetch(`/web/aula/deletar/${aulaId}`, { method: 'POST' })
        .then(response => {
            if (response.ok) {
                alert('Aula desmarcada com sucesso!');
                location.reload();
            } else {
                response.text().then(msg => alert('Erro ao desmarcar: ' + msg));
            }
        })
        .catch(() => alert('Erro ao desmarcar aula.'));
}
