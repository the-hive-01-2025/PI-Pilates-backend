document.addEventListener('DOMContentLoaded', function() {
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

    // Attach event listeners for buttons that were inline onclick
    document.querySelectorAll('.btn-agendar').forEach(button => {
        button.addEventListener('click', abrirModalAgendamento);
    });

    document.querySelectorAll('.fechar').forEach(span => {
        span.addEventListener('click', function() {
            const modal = this.closest('.modal-detalhes');
            if (modal) {
                modal.style.display = 'none';
            }
        });
    });

    window.addEventListener('click', function(event) {
        const modalDetalhes = document.getElementById('modalDetalhes');
        const modalAgendamento = document.getElementById('modalAgendamento');
        const modalPresenca = document.getElementById('modalPresenca');
        const modalCancelada = document.getElementById('modalCancelada');

        if (modalDetalhes && event.target === modalDetalhes) fecharDetalhes();
        if (modalAgendamento && event.target === modalAgendamento) fecharModalAgendamento();
        if (modalPresenca && event.target === modalPresenca) fecharModalPresenca();
        if (modalCancelada && event.target === modalCancelada) fecharModalCancelada();
    });
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

function abrirModalAgendamento() {
    document.getElementById('modalAgendamento').style.display = 'flex';
}

function fecharModalAgendamento() {
    document.getElementById('modalAgendamento').style.display = 'none';
}

let presencaAulaId = null;

function abrirModalPresenca(aulaId, alunosStr, observacao = '', presentesStr = '') {
    presencaAulaId = aulaId;
    const modal = document.getElementById('modalPresenca');
    const nomeDiv = document.getElementById('nomeAula');
    nomeDiv.textContent = '';
    const listaDiv = document.getElementById('listaAlunosPresenca');
    const obsDiv = document.getElementById('observacaoAula');
    listaDiv.innerHTML = '';

    const alunos = alunosStr ? alunosStr.split(',') : [];
    const presentes = presentesStr ? presentesStr.split(',') : [];

    alunos.forEach((nome, idx) => {
        const id = `aluno-presenca-${idx}`;
        const div = document.createElement('div');
        div.style.display = 'flex';
        div.style.alignItems = 'center';
        div.style.marginBottom = '8px';
        const checked = presentes.includes(nome.trim()) ? 'checked' : '';
        div.innerHTML = `
            <input type="checkbox" id="${id}" name="presenca" value="${nome.trim()}" style="margin-right:8px;" ${checked}>
            <label for="${id}">${nome.trim()}</label>
        `;
        listaDiv.appendChild(div);
    });

    obsDiv.textContent = observacao || 'Sem observações.';

    modal.style.display = 'flex';
}

function enviarPresenca(event) {
    event.preventDefault();

    const checkboxes = document.querySelectorAll('#listaAlunosPresenca input[type="checkbox"]:checked');
    const alunosPresentes = Array.from(checkboxes).map(cb => cb.value);

    fetch(`/web/recepcionista/agendamento/presenca/${presencaAulaId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(alunosPresentes)
    })
    .then(response => {
        if (response.ok) {
            alert('Presenças salvas com sucesso!');
            fecharModalPresenca();
            window.location.reload();
        } else {
            alert('Erro ao salvar presenças.');
        }
    })
    .catch(() => alert('Erro ao salvar presenças.'));
}

function fecharModalPresenca() {
    document.getElementById('modalPresenca').style.display = 'none';
}

function abrirModalCancelada(id, data, horario, estudio, alunos, obs) {
    window.aulaCanceladaId = id;
    window.aulaCanceladaData = data;
    window.aulaCanceladaHorario = horario;
    window.aulaCanceladaEstudio = estudio;
    window.aulaCanceladaAlunos = alunos;
    window.aulaCanceladaObs = obs;
    document.getElementById('modalCancelada').style.display = 'flex';
}

function fecharModalCancelada() {
    document.getElementById('modalCancelada').style.display = 'none';
}

function remarcarAula() {
    fecharModalCancelada();
    // Deleta a aula antiga antes de abrir o modal para remarcar
    fetch(`/web/aula/deletar/${window.aulaCanceladaId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    })
    .then(response => {
        if (response.ok) {
            // Preenche os campos do modal de agendamento
            document.getElementById('dataAula').value = window.aulaCanceladaData;
            document.getElementById('horarioAula').value = window.aulaCanceladaHorario;
            // Se tiver select de estúdio/alunos, preencha aqui também
            // document.getElementById('estudioAula').value = window.aulaCanceladaEstudio;
            // document.getElementById('alunosAula').value = window.aulaCanceladaAlunos;
            document.getElementById('observacaoAula').value = window.aulaCanceladaObs;
            // Altere o action do form para PUT ou POST conforme seu backend
            document.getElementById('formAgendamento').action = '/web/recepcionista/agendamento/salvar';
            document.getElementById('modalAgendamento').style.display = 'flex';
        } else {
            alert('Erro ao deletar a aula antiga para remarcar.');
        }
    })
    .catch(() => alert('Erro ao deletar a aula antiga para remarcar.'));
}

function deletarAulaDefinitivo() {
    if (confirm('Tem certeza que deseja excluir definitivamente esta aula?')) {
        fetch(`/aula/${window.aulaCanceladaId}`, { method: 'DELETE' })
            .then(response => {
                if (response.ok) {
                    alert('Aula excluída com sucesso!');
                    window.location.reload();
                } else {
                    response.text().then(msg => alert('Erro ao excluir: ' + msg));
                }
            })
            .catch(() => alert('Erro ao excluir aula.'));
    }
    fecharModalCancelada();
}
