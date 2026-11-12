export default function StatCard({ label, value, hint, icon, badge, variant = "default" }) {
  return (
    <section className={`stat-card stat-card-${variant}`}>
      <div className="stat-icon" aria-hidden="true">{icon}</div>
      <div className="stat-body">
        <div className="stat-header">
          <p className="stat-label">{label}</p>
          {badge && <span className="stat-badge">{badge}</span>}
        </div>
        <p className="stat-value">{value}</p>
        <p className="stat-hint">{hint}</p>
      </div>
    </section>
  );
}
