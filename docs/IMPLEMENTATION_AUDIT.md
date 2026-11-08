# HireNepal implementation audit

Audit date: 2026-11-07

## Scope and evidence

This audit covers the complete tracked application source, lifecycle scripts,
package manifest, generated Payload types, the running Make preview, and the
provided HireNepal admin reference image.

No feature is marked **REAL** unless the available environment allowed it to be
executed and verified. The current Make file does not have a resolvable
platform-managed Payload environment ID, so database-backed paths cannot be
tested and are classified accordingly.

Status definitions:

- **REAL**: implemented and tested in this audit.
- **PARTIAL**: some implementation exists, but the end-to-end contract is
  incomplete or unsafe.
- **MOCK**: simulated with component state, static arrays, hardcoded values, or
  placeholder responses.
- **MISSING**: no implementation exists.
- **BLOCKED**: implementation or verification requires unavailable platform
  infrastructure or external credentials.

## 1. Architecture inventory

### Frontend

- Next.js `16.4.2`, App Router.
- React `19.3.1`.
- TypeScript `6.0.3`, strict mode.
- Tailwind CSS `4.x` is installed, although the main app also relies on a large
  global stylesheet.
- The customer, worker, and app-admin experiences are rendered from one client
  component, `src/components/HireNepalApp.tsx`, on the `/` route. Navigation
  between those experiences is local React state rather than URL routes.

### Backend

- Payload `4.0.0-beta.6` integrated with Next.js.
- `@payloadcms/figma` `1.0.0-beta.1` owns the Figma Cloud database, file
  storage, Payload Admin authentication, and deployment runtime.
- There is no Django, Django REST Framework, Channels, Redis, or Python project.
  Adding one would create a competing backend. The existing Payload backend
  should be repaired and extended instead.

### Database configuration

- `src/payload.config.ts` correctly uses `buildFigmaConfig()`.
- In this Figma-managed template, PostgreSQL configuration is platform-owned
  and intentionally not declared with a local `db` adapter or connection string.
- The running environment currently cannot resolve `FIGMA_EID`. Payload startup,
  REST requests, Admin, production builds, and database verification therefore
  fail before connecting.
- No PostgreSQL connection or persistence operation could be verified.
- Per project rules, adding a raw PostgreSQL adapter, migration files, a local
  database URL, or dummy platform secrets would be incorrect.

### Collections (database models)

| Collection | Present fields / purpose | Audit result |
| --- | --- | --- |
| `admins` | Payload CMS administrators | **BLOCKED** — schema exists; platform Admin runtime returns 500 |
| `users` | Auth-enabled app users, customer/worker role, account status, phone, city, avatar, basic worker profile, hidden app-admin flag | **PARTIAL** — schema and access code exist; database behavior is unverified and the authorization model is incomplete |
| `bookings` | Customer, worker, service, description, schedule, address, price, status | **PARTIAL** — schema exists but is not connected to the frontend and has unsafe update permissions |
| `media` | Public upload collection with required alt text, folders, and tags | **PARTIAL** — upload support exists, but no app upload flow was found and private documents cannot use this public collection |
| `folders` | Media folders | **BLOCKED** — schema only; database unavailable |
| `tags` | Media tags | **BLOCKED** — schema only; database unavailable |

There are no migration files. This generated Make template applies Payload
schema through the platform runtime; application-authored SQL/DDL migrations are
not part of its supported deployment model.

### Existing API surface

- Payload REST catch-all: `/api/[...slug]`, with GET, POST, PATCH, PUT, DELETE,
  and OPTIONS handlers.
- Payload GraphQL: `/api/graphql`.
- Payload GraphQL playground: `/api/graphql-playground`.
- Payload Admin: `/admin`.
- Payload Admin logout redirect: `/admin/logout`.
- Server Actions for app registration, login, logout, and the app-admin
  challenge.

There are no custom domain endpoints for health, profiles, search, verification,
quotations, messages, payments, notifications, disputes, or audit logs.

### Environment variables

Application source reads:

- `FIGMA_PUBLIC_URL` — platform-managed preview origin.
- `FIGMA_CONTENT_API_CONTENT_SYSTEM_ID` — platform-managed.
- `FIGMA_CONTENT_API_URL` — platform-managed.
- `FIGMA_OAUTH_CLIENT_ID` — platform-managed Payload Admin/Figma value, not
  public app Google OAuth.
- `NEXT_PUBLIC_SITE_URL` — build-time public site origin with a localhost
  fallback.
- `NODE_ENV` — cookie security behavior.

The failing Figma Payload runtime also requires a platform-resolved
`FIGMA_EID`. This is not a user-managed secret and must not be hardcoded.

No user-managed integration credentials are currently referenced. There are no
email, Google app OAuth, eSewa, Khalti, push, maps, AI, or Redis settings.

### Existing integrations

- Figma Cloud / Payload runtime: **BLOCKED** by the missing environment binding.
- Payload MCP plugin: configured, but dependent on the same blocked runtime.
- Figma image loader: configured.
- Optional Google Analytics site setting: present.
- Payments, transactional email, Google app OAuth, maps, push notifications,
  AI, and real-time messaging: **MISSING**.

## 2. Feature classification

| Feature | Status | Evidence and gaps |
| --- | --- | --- |
| Public landing UI | **REAL** | `GET /` returned HTTP 200 and rendered the application shell |
| Customer registration | **BLOCKED** | Server Action and Payload create call exist, but the Payload database is unavailable, so no record could be created or verified |
| Worker registration | **BLOCKED** | Same flow creates a pending worker with a minimal profile, but persistence could not be tested |
| Login | **BLOCKED** | Uses Payload `login()` and an HTTP-only app cookie; no real credential flow could be executed |
| Logout | **PARTIAL** | Deletes local cookies, but no server-side token revocation exists and the full flow is untested |
| Password hashing | **BLOCKED** | Delegated to Payload auth, as appropriate, but could not be verified against a stored record |
| Current-user session | **BLOCKED** | JWT cookie resolution code exists; database-backed user resolution cannot run |
| Refresh tokens | **MISSING** | No refresh-token rotation or refresh endpoint |
| Token revocation | **MISSING** | Logout removes cookies only |
| Email verification | **MISSING** | No verification token, email provider, or verified-email state |
| Password reset | **MISSING** | No forgot/reset flow or email integration |
| Google OAuth | **MISSING** | No generated-app OAuth framework or credentials |
| Customer/worker authorization | **PARTIAL** | Collection roles and some access rules exist, but booking mutations are over-broad and no protected domain API flow is implemented |
| App administrator authorization | **PARTIAL** | Hidden `isAppAdmin` boolean and server-side dashboard gate exist; there is no normal-admin/Super-Admin distinction |
| App administrator challenge | **PARTIAL** | Uses two fixed recovery questions and unsalted SHA-256 answers; this is not genuine 2FA and has no attempt limits or audit trail |
| CMS administrator authentication | **BLOCKED** | Correctly separated in `admins`, but `/admin` returns 500 while the Figma backend is unbound |
| Profile editing | **MISSING** | No form or mutation updates a user profile |
| Worker profile creation | **PARTIAL** | Minimal embedded worker fields exist; no complete profile workflow |
| Worker skills | **MISSING** | No skills model or field |
| Service categories | **MOCK** | Static arrays and select options; no category collection |
| Worker prices | **PARTIAL** | `dailyRate` exists in schema but the UI displays hardcoded prices and does not update it |
| Worker locations | **PARTIAL** | One city select exists; no service-area model |
| Worker availability | **MOCK** | Dashboard switch changes React state only |
| Portfolio uploads | **MOCK** | Portfolio images are local static files; no relationship or upload mutation |
| Private verification documents | **MISSING** | No protected upload collection or document fields |
| Verification application | **MISSING** | Only a worker `verified` checkbox exists |
| Verification review/decision | **MISSING** | No application, reviewer, reason, decision history, or admin action |
| Worker discovery | **MOCK** | Workers are a static array in `HireNepalApp.tsx` |
| Worker search/filter/sort | **MOCK** | Operates only on the static client array; no API, database query, or pagination |
| Worker detail | **MOCK** | One hardcoded Ramesh profile, portfolio, review, price, and availability |
| Saved workers | **MOCK** | Stored only in component state |
| Booking creation | **MOCK** | Form submission only switches to the confirmation screen |
| Booking image upload | **MOCK** | Uses browser blob preview URLs; no upload occurs |
| Booking persistence | **MISSING** | Frontend never calls Payload or an API |
| Quotation | **MISSING** | No quotation model, endpoint, or workflow |
| Booking transitions | **MOCK** | Confirmation, cancellation, and rescheduling are local screen/state changes |
| Messaging | **MOCK** | Hardcoded messages, local array, and `setTimeout` auto-reply |
| Conversation persistence | **MISSING** | No conversation/message collections |
| Real-time updates | **MISSING** | No Channels, WebSocket, SSE, or supported real-time provider |
| Admin dashboard user/worker/booking counts | **BLOCKED** | Server-side Payload count queries exist for app admins, but database execution is unavailable |
| Admin revenue and analytics | **MOCK** | Hardcoded revenue, growth lines, percentages, and category/location data |
| Admin worker list | **PARTIAL** | Payload query exists, but hardcoded fallback workers are shown when no records are returned |
| Admin worker management | **MOCK** | Search is client-only; row menus and filters have no mutation |
| Admin booking management | **MOCK** | Static booking array and inactive filters |
| Admin category management | **MISSING** | Button has no action and there is no category collection |
| Complaints/disputes | **MISSING** | No schema, API, or UI |
| Audit logs | **MISSING** | Settings row has no action; no audit model or hooks |
| Database-driven settings | **MISSING** | Settings rows are inert |
| Wallet/top-up | **MOCK** | Button adds NPR 1,000 to local component state and shows a timed success toast |
| eSewa | **MISSING** | No adapter, callback, backend validation, or credentials |
| Khalti | **MISSING** | No adapter, callback, backend validation, or credentials |
| Notifications | **MOCK** | Hardcoded notification cards and badge |
| Maps/location integration | **MISSING** | City values are static; no provider integration |
| Push notifications | **MISSING** | No provider or subscription model |
| AI provider | **MISSING** | No provider or server-side adapter |
| Health endpoint | **MISSING** | `/api/health/` redirects to `/api/health`, which returned HTTP 500 through the Payload catch-all |
| Automated tests | **MISSING** | No test files, test runner, or test script |
| Android application/build | **MISSING** | This repository is a web app only |

## 3. Fake and hardcoded behavior inventory

The following visible behavior must not be represented as complete:

- `services` and `workers` are static arrays.
- Customer worker cards, worker details, reviews, availability, locations,
  prices, and verification badges are hardcoded.
- Worker dashboard requests, jobs, earnings, graphs, and review counts are
  hardcoded. Availability is local state.
- Booking submission calls `go('confirmed')` without validation, API access, or
  persistence.
- Booking photos are browser-only object URLs.
- Booking cancellation and rescheduling only change local state/screens.
- Chat uses a local array and a `setTimeout` simulated worker response.
- Admin revenue, charts, bookings, reports, filters, and settings are hardcoded
  or inert.
- The worker list has a hardcoded fallback, which can conceal an empty database.
- Wallet top-up fabricates a successful balance increase.
- Notifications, promotions, favourites, review counts, and profile completion
  are static or local-only.
- Numerous visible buttons have no handler, including worker quick actions,
  admin user/category management, worker filters, service address, phone,
  attachment, settings, and several account rows.

## 4. Security and data-integrity findings

### Critical

1. **Backend unavailable.** Payload cannot resolve `FIGMA_EID`, so the database,
   Admin, REST API, health check, uploads, and production build do not work.
2. **Booking update authorization is too broad.** Either related user can update
   the complete booking document, including customer, worker, price, service,
   schedule, and status. Field-level permissions and durable transition rules
   are absent.
3. **Worker relationship eligibility is not enforced at the collection
   boundary.** `filterOptions` affects selection UI but is not a durable
   validation rule. The selected user is not proven to be an active, verified
   worker.
4. **Any app user can create a booking.** The create rule accepts workers as
   well as customers, and the hook assigns the current user as customer.

### High

1. App administration is a single hidden boolean with no administrator role
   hierarchy. The UI labels every app administrator “Super Admin.”
2. The fixed-question admin challenge is not 2FA. It has no attempt throttling,
   lockout, audit event, authenticator secret, or recovery-code lifecycle.
3. Verification is a mutable checkbox rather than an application and immutable
   review history.
4. The shared `media` collection is publicly readable and is unsuitable for
   identity or qualification documents.
5. Errors in registration and login are broadly swallowed, which hides
   operational failures from users and operators.

### Medium

1. Booking references use a truncated timestamp and can collide despite a
   unique constraint.
2. No rate limiting, abuse controls, or security-event logging was found.
3. No email ownership verification exists.
4. No token revocation or refresh-token rotation exists.
5. `next.config.ts` sets `typescript.ignoreBuildErrors: true`, allowing
   production builds to continue despite TypeScript failures.

## 5. Broken configuration and runtime findings

| Finding | Result |
| --- | --- |
| TypeScript check | **PASS** — `pnpm exec tsc --noEmit` exited 0 |
| Formatting check | **FAIL** — generated `src/app/(payload)/admin/importMap.js` is not formatted |
| `/` preview | **PASS** — HTTP 200 |
| `/api/health/` | **FAIL** — 308 to `/api/health`, then HTTP 500 |
| `/api/users` | **FAIL** — HTTP 500 |
| `/api/bookings` | **FAIL** — HTTP 500 |
| `/admin` | **FAIL** — HTTP 500 |
| Payload startup | **FAIL** — “Environment ID could not be resolved” |
| Payload Admin bundle | **FAIL** — `src/app/(payload)/layout.tsx` imports missing `./custom.css` |
| Image runtime | **WARN** — Figma custom loader reports that local image width is not implemented |
| Tests | **NOT RUN** — no test framework or tests exist |
| Database persistence | **NOT RUN** — platform backend is unavailable |
| Migrations | **NOT RUN** — no migration files; platform schema runtime unavailable |
| Production deploy verification | **FAIL** — blocked before compilation by missing platform environment binding |
| Deploy-preview verification | **FAIL** — same blocker |

No broken TypeScript imports were found by `tsc`, but the missing CSS import is
a real runtime/bundler error that TypeScript does not detect.

## 6. Recommended recovery order

This order preserves the existing architecture and the requested UI:

1. Restore the Figma Payload backend binding so `buildFigmaConfig()` can resolve
   the managed PostgreSQL environment.
2. Repair the missing Payload Admin stylesheet import and make `/admin`,
   `/api/users`, and `/api/bookings` return intentional statuses.
3. Add `GET /api/health/` with a real Payload/database query and test both
   healthy and failed-database responses.
4. Add a test framework and establish isolated backend integration tests.
5. Repair app authentication and authorization: explicit app roles, safe
   session handling, email verification/reset provider, and tested endpoint
   permissions. Add Google OAuth only after its credentials are configured.
6. Evolve the domain schema safely: categories, complete worker profiles,
   private verification uploads/applications/decisions, booking quotations and
   transition history, conversations/messages, disputes, audit events, and
   integration records.
7. Connect one vertical slice at a time, beginning with registration/login and
   then worker profiles. Remove each corresponding mock only after the real
   database-backed flow passes.
8. Implement payment adapters last among core transactional flows. Keep them
   disabled until sandbox credentials exist and backend callback verification
   passes.

Potential schema changes in these phases include new required fields,
relationships, and collections. Existing data must be inspected after backend
recovery, and destructive or constraint-tightening changes require confirmation
before implementation.

## 7. Audit phase report

### Files created

- `docs/IMPLEMENTATION_AUDIT.md`

### Files modified

- None.

### APIs implemented

- None in this audit phase.

### Models and migrations

- None created or changed.

### Commands executed

- Repository file, source, environment-reference, mock-pattern, and route
  searches with `rg`, `find`, `sed`, and `wc`.
- `figma attachments get ...`
- `figma make preview status`
- HTTP probes with `curl`
- `figma logs tail .figma/make/dev --stream stderr --lines 160`
- `pnpm exec tsc --noEmit`
- `pnpm exec oxfmt --check src package.json next.config.ts`

### Actual test results

- Static TypeScript check passed.
- Formatting check failed on the generated Payload import map.
- The frontend route returned 200.
- Payload REST, Admin, and the requested health path returned 500.
- No database-backed functional test could run.

### Remaining problems

- Platform Payload/PostgreSQL binding is unavailable.
- Payload Admin has a missing CSS import.
- All missing, mock, partial, and security findings listed above remain.

### External credentials or infrastructure required

- Immediate infrastructure requirement: reconnect/provision this Make file's
  platform-managed Payload environment. `FIGMA_EID` must be supplied by Figma,
  not entered as a user secret.
- Later credentials: transactional email provider, Google OAuth client,
  eSewa sandbox, Khalti sandbox, maps provider, push provider, and AI provider.
  Exact variables should be requested only when each integration phase begins.
