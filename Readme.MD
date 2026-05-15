# FirstClub Membership Program

## Overview

1. User → Register and manage membership subscriptions
2. MembershipPlan → Monthly, Quarterly, Yearly plans with pricing
3. MembershipTier → Silver, Gold, Platinum tiers with configurable benefits
4. Subscription → Subscribe to a plan + tier, upgrade/downgrade, cancel
5. TierEligibility → Strategy pattern for configurable tier upgrade criteria

## Business Rules

A user has a name, email and membership status: ACTIVE or INACTIVE.

A membership plan has a type (MONTHLY / QUARTERLY / YEARLY), price, and duration in days (30 / 90 / 365). Plans are seeded at startup and are not created via API.

A membership tier has a type (SILVER / GOLD / PLATINUM) and benefits: discount percentage, free delivery, exclusive deals, and priority support. Tiers are seeded at startup with configurable criteria.

A subscription links a user to a plan and a tier. It has a start date, expiry date (calculated from plan duration), and status: ACTIVE, CANCELLED, or EXPIRED.

A user can only have one ACTIVE subscription at a time. Attempting to create a second active subscription returns 409 Conflict.

Tier upgrade and downgrade is allowed on any ACTIVE subscription. A user cannot switch to the same tier they are already on.

A subscription can only be cancelled if its status is ACTIVE. Cancelling a subscription sets the user's membership status back to INACTIVE.

Tier eligibility is determined by configurable strategies — order count or order value. Adding a new eligibility criteria requires only a new strategy class with zero changes to existing code.

## Entities

```
User {
    id,
    name,
    email,
    membershipStatus (ACTIVE / INACTIVE),
    createdAt,
    updatedAt
}

MembershipPlan {
    id,
    planType (MONTHLY / QUARTERLY / YEARLY),
    price,
    durationInDays,
    createdAt
}

MembershipTier {
    id,
    tierType (SILVER / GOLD / PLATINUM),
    discountPercentage,
    freeDelivery,
    exclusiveDeals,
    prioritySupport,
    minOrders,
    minOrderValue,
    createdAt
}

Subscription {
    id,
    userId,
    planId,
    tierId,
    status (ACTIVE / CANCELLED / EXPIRED),
    startDate,
    expiryDate,
    createdAt,
    updatedAt
}
```

## Enums

```
PlanType         { MONTHLY, QUARTERLY, YEARLY }
TierType         { SILVER, GOLD, PLATINUM }
SubscriptionStatus { ACTIVE, CANCELLED, EXPIRED }
MembershipStatus { ACTIVE, INACTIVE }
```

## API Endpoints

### Plans
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /plans | Get all available membership plans |

### Tiers
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /tiers | Get all membership tiers with benefits |

### Users
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /users | Register a new user. Email must be unique. |

### Subscriptions
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /subscriptions | Subscribe to a plan and tier |
| PATCH | /subscriptions/{id}/tier | Upgrade or downgrade membership tier |
| PATCH | /subscriptions/{id}/cancel | Cancel an active subscription |
| GET | /subscriptions/{userId}/current | Get current active subscription with full details |
| GET | /subscriptions/{userId}/eligible-tier | Get highest tier user is eligible for |

## Schema Design

```
USERS {
    id              String    PK
    name            String
    email           String
    membershipStatus String
    createdAt       Date
    updatedAt       Date
}

MEMBERSHIP_PLANS {
    id              String    PK
    planType        String
    price           Double
    durationInDays  Integer
    createdAt       Date
}

MEMBERSHIP_TIERS {
    id                  String    PK
    tierType            String
    discountPercentage  Double
    freeDelivery        Boolean
    exclusiveDeals      Boolean
    prioritySupport     Boolean
    minOrders           Integer
    minOrderValue       Double
    createdAt           Date
}

SUBSCRIPTIONS {
    id          String    PK
    userId      String    FK
    planId      String    FK
    tierId      String    FK
    status      String
    startDate   Date
    expiryDate  Date
    createdAt   Date
    updatedAt   Date
}
```

## Relationships

```
USERS         : SUBSCRIPTIONS    (One to Many)
MEMBERSHIP_PLANS : SUBSCRIPTIONS (One to Many)
MEMBERSHIP_TIERS : SUBSCRIPTIONS (One to Many)
```

## Design Patterns Used

**Strategy Pattern** — TierEligibilityStrategy interface with two implementations:
- `OrderCountStrategy` — eligible if user has placed more than X orders
- `OrderValueStrategy` — eligible if user's monthly order value exceeds X

Adding a new eligibility criteria = one new class, zero existing code changes.

**Builder Pattern** — Used for `MembershipTier` and `Subscription` to handle multiple optional fields cleanly.

## Concurrency

- `ConcurrentHashMap` used in all repositories for thread-safe reads and writes
- `ConcurrentHashMap.newKeySet()` for thread-safe email uniqueness tracking
- `ReentrantLock` in `SubscriptionRepository` to prevent race condition on duplicate active subscription check

## Tech Stack

- Java 21
- Spring Boot 3.x
- Spring Web MVC
- Lombok
- In-memory storage (ConcurrentHashMap)
- Gradle

## How to Run

```bash
./gradlew bootRun
```

App starts on `http://localhost:8080`

## Sample Postman Flow

```
1. GET  /plans                              → note plan IDs
2. GET  /tiers                              → note tier IDs
3. POST /users                              → create user, note userId
4. POST /subscriptions                      → subscribe with userId + planId + tierId
5. GET  /subscriptions/{userId}/current     → view active subscription
6. PATCH /subscriptions/{id}/tier           → upgrade to gold
7. GET  /subscriptions/{userId}/eligible-tier → check tier eligibility
8. PATCH /subscriptions/{id}/cancel         → cancel subscription
```