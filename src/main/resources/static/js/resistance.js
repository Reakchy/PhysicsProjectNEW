document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('calcForm');
    const metalSelect = document.getElementById('metal');
    const resultsDiv = document.getElementById('ajaxResults');
    const errorDiv = document.getElementById('ajaxError');
    const resultsGrid = document.getElementById('resultsGrid');

    // Справочные данные
    const METAL_DATA = {
        'Медь': { rho1: 0.0175, alpha: 0.0043 },
        'Алюминий': { rho1: 0.0280, alpha: 0.0040 },
        'Железо': { rho1: 0.1000, alpha: 0.0060 },
        'Вольфрам': { rho1: 0.0550, alpha: 0.0045 },
        'Нихром': { rho1: 1.1000, alpha: 0.0004 },
        'Серебро': { rho1: 0.0160, alpha: 0.0038 }
    };

    // Автозаполнение ρ₁ и α
    metalSelect.addEventListener('change', function() {
        const data = METAL_DATA[this.value];
        if (data) {
            document.getElementById('rho1').value = data.rho1.toFixed(6);
            document.getElementById('alpha').value = data.alpha.toFixed(6);
        }
    });

    // Инициализация при загрузке
    if (metalSelect.value) {
        metalSelect.dispatchEvent(new Event('change'));
    }

    // Сброс формы
    document.getElementById('resetBtn').addEventListener('click', function() {
        form.reset();
        resultsDiv.style.display = 'none';
        errorDiv.style.display = 'none';
        updateWireColor(20); // сброс цвета нити
        if (metalSelect.value) metalSelect.dispatchEvent(new Event('change'));
    });

    // Отправка через fetch
    form.addEventListener('submit', async function(e) {
        e.preventDefault();

        errorDiv.style.display = 'none';
        resultsDiv.style.display = 'none';
        form.classList.add('loading');

        const data = {
            metal: metalSelect.value,
            t1: +document.getElementById('t1').value,
            t2: +document.getElementById('t2').value,
            length: +document.getElementById('length').value,
            diameterMm: +document.getElementById('diameterMm').value,
            rho1: +document.getElementById('rho1').value,
            alpha: +document.getElementById('alpha').value
        };

        // Валидация
        if ([data.t1, data.t2, data.length, data.diameterMm].some(isNaN)) {
            showError('❌ Заполните все числовые поля');
            form.classList.remove('loading');
            return;
        }

        try {
            const res = await fetch('/api/calculate', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            });
            const json = await res.json();

            if (!res.ok) throw new Error(json.error || 'Ошибка расчёта');

            showResults(json);
            updateWireColor(data.t2);

        } catch (err) {
            showError(err.message);
        } finally {
            form.classList.remove('loading');
        }
    });

    function showResults(data) {
        const items = [
            { l: 'Металл', v: data.metal },
            { l: 'Δt', v: fmt(data.dt, 4) + ' °C' },
            { l: 'k', v: fmt(data.k, 6) },
            { l: 'S', v: fmt(data.areaMm2, 6) + ' мм²' },
            { l: 'ρ₁', v: fmt(data.rho1, 6) + ' Ом·мм²/м' },
            { l: 'ρ₂', v: fmt(data.rho2, 6) + ' Ом·мм²/м' },
            { l: 'Δρ', v: fmt(data.deltaRho, 6) + ' Ом·мм²/м' },
            { l: 'Изменение', v: fmt(data.percent, 4) + ' %' },
            { l: 'R₁', v: fmt(data.r1, 6) + ' Ом' },
            { l: 'R₂', v: fmt(data.r2, 6) + ' Ом' }
        ];
        resultsGrid.innerHTML = items.map(i =>
            `<div class="result-item"><strong>${i.l}</strong><span>${i.v}</span></div>`
        ).join('');
        resultsDiv.style.display = 'block';
    }

    function updateWireColor(t2) {
        const outer = document.getElementById('wireOuter');
        const core = document.getElementById('wireCore');
        if (!outer || !core) return;

        outer.className = core.className = '';
        if (t2 > 300) {
            outer.classList.add('wire-hot');
            core.classList.add('wire-core-hot');
        } else if (t2 > 100) {
            outer.classList.add('wire-warm');
            core.classList.add('wire-core-warm');
        } else {
            outer.classList.add('wire-cold');
            core.classList.add('wire-core-cold');
        }
    }

    function showError(msg) {
        errorDiv.textContent = msg;
        errorDiv.style.display = 'block';
    }

    function fmt(num, dec) {
        return num == null ? '—' : Number(num).toFixed(dec).replace(/\.?0+$/, '');
    }
});